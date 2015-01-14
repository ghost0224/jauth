package com.hp.security.jauth.core.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.service.ACLService;
import com.hp.security.jauth.core.service.AssociateUserService;
import com.hp.security.jauth.core.service.AuthLogService;
import com.hp.security.jauth.core.service.AuthService;
import com.hp.security.jauth.core.service.ConfigService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.JAuthLog;
import com.hp.security.jauth.core.util.MessageContext;
import com.hp.security.jauth.core.util.RuleUtil;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	private AssociateUserService userService;
    private ACLService aclService;
    private RuleUtil ruleUtil;
    private AuthLogService authLogService;
    private SystemInit systemInit;
    private Logger log = Logger.getLogger(this.getClass().getName());
    
    @Autowired
    public void setUserService(AssociateUserService userService) {
		this.userService = userService;
	}

    @Autowired
	public void setAclService(ACLService aclService) {
		this.aclService = aclService;
	}
    
    @Autowired
	public void setRuleUtil(RuleUtil ruleUtil) {
		this.ruleUtil = ruleUtil;
	}
    
    @Autowired
    public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}
    
	@Autowired
	public void setSystemInit(SystemInit systemInit) {
		this.systemInit = systemInit;
	}
    
	public AssociateUser findAssociateUser(String userId) {
    	AssociateUser associateUser = userService.findByUserId(userId);
        if (null != associateUser && !associateUser.getActivate().equals("Y")) {
        	String message = MessageContext.getMessage("AUTH_ERROR_13", new String[]{"user", associateUser.getUserId()});
            log.info(message);
            throw new AuthException("AUTH_ERROR_13",message);
        }
        return associateUser;
    }

    public String getFinalUserId(HttpServletRequest request, String sessionID) {
    	String userId = null;
    	HttpSession session = request.getSession();
    	// get from session
    	userId = (String)session.getAttribute(sessionID);
    	// get from parameters
    	if(null == userId || userId.trim().length() == 0) {
    		userId = request.getParameter(sessionID);
    	}
    	// get from http header
    	if(null == userId || userId.trim().length() == 0) {
    		userId = request.getHeader(SystemInit.sessionID);
    	}
    	if(null == userId || userId.trim().equals("")) {
    		String message = MessageContext.getMessage("AUTH_ERROR_11", new String[]{"session", sessionID});
            log.error(message);
            throw new AuthException("AUTH_ERROR_11",message);
        }
    	return userId;
    }
    
    public boolean getPermission(String authorizationCheck, HttpServletRequest request, ServletResponse response, Map<String, String> CO, String url, AuthLog authLog) throws IOException, ServletException {
    	HttpSession session = request.getSession();
        // get User ID
        String userId = getFinalUserId(request, SystemInit.sessionID);
        AssociateUser associateUser = findAssociateUser(userId);
        authLog.setUserId(userId);
        // get current user's ACL from session
        Object aclMapObj = session.getAttribute(SystemInit.sessionACL);
        // if not in session, create it
        if (null == aclMapObj) {
            aclMapObj = aclService.findAllByUser(associateUser.getAssociateUserId());
            session.setAttribute(SystemInit.sessionACL, aclMapObj);
        }
        Map<String, ACL> aclMap = (HashMap<String, ACL>) aclMapObj;
        if(CO.size() == 0) {
        	// find Controller and Operation
        	getFinalCAndO(request, url, CO);
        }
        // find permission
        boolean result = aclService.findPermissionByControllerAndOperation(aclMap, CO.get(Constants.AUTH_C), CO.get(Constants.AUTH_O));
        
		//log result for permission
    	authLog.setController(CO.get(Constants.AUTH_C));
    	authLog.setOperation(CO.get(Constants.AUTH_O));
    	if("Y".equalsIgnoreCase(authorizationCheck)) {
    		authLog.setResult(result?Constants.SUCCESS:Constants.AUTHORIZATION_FAILURE);
    	} else {
    		result = true;
    		authLog.setResult(Constants.SUCCESS);
    	}
    	authLog.setJauthCost(System.currentTimeMillis() - authLog.getStartTime());
    	log.info("user:" + userId + "url:" + url + ", authC:" + CO.get(Constants.AUTH_C) + ", authO: " + CO.get(Constants.AUTH_O) + ", result: " + result);
    	return result;
    }
    
    public void recordLog(AuthLog authLog) {
    	if(null != authLog && authLog.getUserId() != null && authLog.getUserId().length() > 0) {
    		if(SystemInit.logPath.length() > 0) {
    			JAuthLog.log(authLog);
    		}
    		if(SystemInit.dbLogEnabled.equalsIgnoreCase("Y")) {
    			authLogService.save(authLog);
    		}
    	}
    }
    
    private Map<String, String> getFinalCAndO(HttpServletRequest request, String formatUrl, Map<String, String> finalCO) throws IOException {
    	// try to get from parameters
    	String authC = request.getParameter(Constants.AUTH_C);
        String authO = request.getParameter(Constants.AUTH_O);
        if (authC == null || authC.trim().equals("")) {
        	//try to get from SOAP Message
            if (null != systemInit.soapHook && (authO == null || authO.trim().length() == 0) && SystemInit.soapEnabled.equals("Y")) {
            	authO = systemInit.soapHook.getOperationName(request);
            	//it's a effective SOAP request
            	if(null != authO)
            		authC = formatUrl;
            }
            
            if (authC == null || authC.trim().equals("")) {
            	//try to get from rules
            	Map<String, String> resultCO = ruleUtil.getControllerAndOperation(formatUrl, authO);
	            if (resultCO == null) {
	            	String message = MessageContext.getMessage("AUTH_ERROR_2", new String[]{"url", formatUrl});
	                log.warn(message);
	                throw new AuthException("AUTH_ERROR_2",message);
	            }
	            authC = resultCO.get(Constants.AUTH_C);
	            if (authO == null || authO.trim().length() == 0) {
	                authO = resultCO.get(Constants.AUTH_O);            	
	            }
            }
        }
        finalCO.put(Constants.AUTH_C, authC);
        finalCO.put(Constants.AUTH_O, authO);
    	return finalCO;
    }

}
