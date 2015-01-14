/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.hook.AuthHook;
import com.hp.security.jauth.core.hook.SoapHook;
import com.hp.security.jauth.core.hook.UserValidationHook;
import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.service.AuthService;
import com.hp.security.jauth.core.service.ConfigService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.ContextHolder;
import com.hp.security.jauth.core.util.ExceptionUtil;
import com.hp.security.jauth.core.util.HttpUtil;
import com.hp.security.jauth.core.util.MessageContext;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
public class AuthFilter implements Filter {

    private ServletContext servletContext;
    private ApplicationContext context;
    private AuthService authService;
    private AuthHook authHook;
    private ExceptionUtil exceptionUtil;
    private SystemInit systemInit;

    private Logger log = Logger.getLogger(this.getClass().getName());

    public void init(FilterConfig filterConfig) throws ServletException {
    	//init components
        this.servletContext = filterConfig.getServletContext();
        context = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        authService = context.getBean(AuthService.class);
        exceptionUtil = context.getBean(ExceptionUtil.class);
        systemInit = context.getBean(SystemInit.class);
        try {
        	authHook = context.getBean(AuthHook.class);
        } catch(NoSuchBeanDefinitionException e) {
        	log.info("no authHook find.");
        }
        try {
        	systemInit.userValidationHook = context.getBean(UserValidationHook.class);
        } catch(NoSuchBeanDefinitionException e) {
        	log.info("no userValidationHook find.");
        }
        try {
        	systemInit.soapHook = context.getBean(SoapHook.class);
        } catch(NoSuchBeanDefinitionException e) {
        	log.info("no SOAP Hook find.");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws RuntimeException, ServletException, IOException {
    	
    	boolean accessible = false;
        // change to AuthHttpServetRequest
    	AuthHttpServletRequestWrapper request = new AuthHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        Map<String, String> CO = new HashMap<String, String>();
        String url = request.getRequestURL().toString();
        url = HttpUtil.getBusinessUrl(request);
        
        //define log
    	AuthLog authLog = new AuthLog();
    	authLog.setStartTime(System.currentTimeMillis());
    	authLog.setInsertDate(new Date());
        String application = request.getContextPath().replaceAll("/", "");
        authLog.setApplication(application);
        
    	//init for wss4j
		ContextHolder.setPassed(false);
		ContextHolder.setURL(url);
		ContextHolder.setRequest(request);
		ContextHolder.setServletResponse(servletResponse);
		ContextHolder.setLog(authLog);
		ContextHolder.setAuthorizationCheck(SystemInit.authorizationCheck);
    	
    	try {
	        //if auth is inactive
	    	if(SystemInit.authActive.equals("N")) {
	    		// pass
	    		accessible = setAllPassed(request, servletResponse, filterchain);
	            return;
	    	}
	    	
	    	//accessible hosts
	    	String host = request.getServerName();
	        if(!accessible) {
	        	String[] accessibleHost = SystemInit.accessibleHost.split(",");
	        	for (String h : accessibleHost) {
	        		if (host.matches(h)) {
	        			log.debug("accessibleHost: " + host);
	        			// pass
	    	    		accessible = setAllPassed(request, servletResponse, filterchain);
	    	    		return;
	        		}
	        	}
	        }
	    	
	    	//accessible pages
	        if(!accessible) {
	        	String[] accessiblePage = SystemInit.accessiblePage.split(",");
	        	for (String page : accessiblePage) {
	        		if (url.matches(page)) {
	        			log.debug("accessiblePage: " + page);
	        			// pass
	    	    		accessible = setAllPassed(request, servletResponse, filterchain);
	    	    		return;
	        		}
	        	}
	        }
	        
	        //protect WSDL link
	        Enumeration<String> e = request.getParameterNames();
            while(e.hasMoreElements()) {
            	String param = e.nextElement();
            	if(param.equalsIgnoreCase("wsdl")
            			//&& (request.getParameter(param) == null || request.getParameter(param).trim().length() == 0 || request.getParameter(param).endsWith(".wsdl"))) 
            			||
            		param.equalsIgnoreCase("xsd")) {
            			//&& (request.getParameter(param) != null || request.getParameter(param).trim().length() > 0))) {
            		CO.put(Constants.AUTH_C, url);
            		CO.put(Constants.AUTH_O, "view");
            		break;
            	}
            }
	        if(SystemInit.protectWSDL.equals("N") && CO.size() > 0) {
	        	// pass
	    		accessible = setAllPassed(request, servletResponse, filterchain);
	    		return;
	        }
	        
	        //to wss4j
	        if(request.hasRequestBody() && (SystemInit.soapEnabled.equals("Y"))) {
	        	accessible = true;
	        }
	        
	        if (accessible) {
	        	// pass
	            filterchain.doFilter(request, servletResponse);
	        } else {
        		// custom process
	        	String notes = null;
            	if(null != authHook && !url.substring(url.indexOf("/")+1).startsWith("jauth/")) {
            		if(!authHook.doProcess(request, authLog)) {
            			if(authLog != null) {
            				//log result for custom valition
                        	authLog.setResult(Constants.AUTHENTICATION_FAILURE);
                        	authLog.setJauthCost(System.currentTimeMillis() - authLog.getStartTime());
                			authLog.setOverallCost(authLog.getJauthCost());
                			authService.recordLog(authLog);
                    	}
            			return;
            		}
            		notes = authHook.getNotes(request);
            		authLog.setNotes(notes);
            	}
            	
            	//find permission
            	boolean result = false;
            	result = authService.getPermission(SystemInit.authorizationCheck, request, servletResponse, CO, url, authLog);
            	
            	if("Y".equalsIgnoreCase(SystemInit.authorizationCheck)) {
            		if (result) {
            			// pass
            			accessible = setAllPassed(request, servletResponse, filterchain);
        	            return;
                    } else {
                    	String message = MessageContext.getMessage("AUTH_ERROR_10", new String[]{"user",authLog.getUserId()}, new String[]{"controller",CO.get(Constants.AUTH_C)}, new String[]{"operation",CO.get(Constants.AUTH_O)});
                        throw new AuthException("AUTH_ERROR_10", message);
                    }
            	} else {
            		// pass
            		accessible = setAllPassed(request, servletResponse, filterchain);
    	            return;
            	}
        	}
    	} catch (RuntimeException e) {
    		if(e instanceof AuthException && ((AuthException)e).getCode().equals("AUTH_ERROR_11")) {
				String loginPage = SystemInit.loginPage;
				if(null != loginPage && loginPage.trim().length() > 0) {
					((HttpServletResponse)servletResponse).sendRedirect("/" + application + loginPage);
        		} else {
        			throwsException(request, servletResponse, e);
        		}
    		} else if(e instanceof AuthException) {
    			String exPage = SystemInit.exceptionPage;
    			if(null != exPage && exPage.trim().length() > 0) {
					request.setAttribute("ex", e);
					request.getRequestDispatcher(exPage).forward(request, servletResponse);
        		} else {
        			throwsException(request, servletResponse, e);
        		}
    		} else {
    			throwsException(request, servletResponse, e);
    		}
    	} finally {
    		//record log
			if(authLog.getJauthCost() == 0) {
				authLog.setJauthCost(System.currentTimeMillis() - authLog.getStartTime());
			}
			authLog.setOverallCost(System.currentTimeMillis() - authLog.getStartTime());
			authService.recordLog(authLog);
    	}
    }
    
    public void destroy() {

    }
    
    private boolean setAllPassed(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterchain) throws IOException, ServletException {
    	ContextHolder.setPassed(true);
        filterchain.doFilter(servletRequest, servletResponse);
        return true;
    }

	public SystemInit getSystemInit() {
		return systemInit;
	}
	
	private void throwsException(HttpServletRequest request, ServletResponse servletResponse, Exception e) throws ServletException, IOException {
		e.printStackTrace();
		String exPage = SystemInit.exceptionPage;
		if(null != exPage && exPage.trim().length() > 0) {
			request.setAttribute("ex", e);
			request.getRequestDispatcher(exPage).forward(request, servletResponse);
		} else {
			exceptionUtil.throwDefaultTemplate(servletResponse, e);
		}
	}
	
}
