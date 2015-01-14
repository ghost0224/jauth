/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.service.ACLService;
import com.hp.security.jauth.core.service.ApplicationService;
import com.hp.security.jauth.core.service.AssociateUserService;
import com.hp.security.jauth.core.service.ConfigService;
import com.hp.security.jauth.core.service.ControllerService;
import com.hp.security.jauth.core.service.GroupService;
import com.hp.security.jauth.core.service.OperationService;
import com.hp.security.jauth.core.service.RoleService;
import com.hp.security.jauth.core.util.FreemarkerUtil;
import com.hp.security.jauth.core.util.MD5;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("/")
public class BaseController {
	
    protected FreemarkerUtil freemarkerUtil;
    
    protected ApplicationService applicationService;

    protected ControllerService controllerService;

    protected OperationService operationService;

    protected GroupService groupService;

    protected RoleService roleService;

    protected AssociateUserService associateUserService;

    protected ACLService aclService;

    protected ConfigService configService;

    @RequestMapping("index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException,
            TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        String view = freemarkerUtil.buildView(root, "main");
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        out.write(view.getBytes());
        out.flush();
        out.close();
    }
    
    @RequestMapping("exception")
    public void exception(HttpServletRequest request, HttpServletResponse response) throws IOException,
            TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        String code = "";
        Exception ex = (Exception)request.getAttribute("ex") ;
        if(null != ex && ex instanceof AuthException) {
        	AuthException aex = (AuthException)ex;
        	code = aex.getCode();
        }
        root.put("code", code);
        root.put("ex", ex);
        String view = freemarkerUtil.buildView(root, "exception");
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        out.write(view.getBytes());
        out.flush();
        out.close();
    }
    
    @RequestMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
    	String error = null;
    	if(null != request.getAttribute("error")) {
    		error = request.getAttribute("error").toString();
    	}
    	Map<String, Object> root = new HashMap<String, Object>();
    	root.put("error", error);
        String view = freemarkerUtil.buildView(root, "login");
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();
        out.write(view.getBytes());
        out.flush();
        out.close();
	}
    
    @RequestMapping("loginProcess")
    public void loginProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		AssociateUser user = associateUserService.findByUserId(userId);
		if(null != user && MD5.getInstance().getMD5ofStr(password).equalsIgnoreCase(user.getPassword())) {
			HttpSession session = request.getSession();
			session.setAttribute(SystemInit.sessionID, userId);
			response.sendRedirect(request.getContextPath() + "/jauth/index");
		} else {
			request.setAttribute("error", "Sorry, username or password is not correct!");
			login(request, response);
		}
	}

    @RequestMapping("getResource")
    public void getResource(String path, HttpServletRequest request, HttpServletResponse response) throws IOException,
            TemplateException {
        if (null != path) {
            if (path.endsWith(".js")) {
                response.setContentType("text/js");
            } else if (path.endsWith(".css")) {
                response.setContentType("text/css");
            } else if (path.endsWith(".gif")) {
                response.setContentType("image/gif");
            } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                response.setContentType("image/jpeg");
            } else if (path.endsWith(".png")) {
                response.setContentType("image/png");
            } else {
                response.setContentType("text/html");
            }
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource("jauth/resources/" + path);
            ServletOutputStream out = response.getOutputStream();
            InputStream is = resource.getInputStream();
            int read = 0;
            byte[] buffer = new byte[8192];
            while ((read = is.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            out.close();
        }
    }

    
    protected String baseReturn(HttpServletRequest request, HttpServletResponse response, Map<String, Object> root, String template) throws IOException, TemplateException {
    	return freemarkerUtil.baseReturn(request, response, root, template);
	}

    /**
     * @param freemarkerUtil
     *            the freemarkerUtil to set
     */
    @Autowired
    public void setFreemarkerUtil(FreemarkerUtil freemarkerUtil) {
        this.freemarkerUtil = freemarkerUtil;
    }

    /**
     * @param controllerService
     *            the controllerService to set
     */
    @Autowired
    public void setControllerService(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    /**
     * @param operationService
     *            the operationService to set
     */
    @Autowired
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }

    /**
     * @param groupService
     *            the groupService to set
     */
    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * @param roleService
     *            the roleService to set
     */
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * @param associateUserService
     *            the associateUserService to set
     */
    @Autowired
    public void setAssociateUserService(AssociateUserService associateUserService) {
        this.associateUserService = associateUserService;
    }

    /**
     * @param aclService
     *            the aclService to set
     */
    @Autowired
    public void setAclService(ACLService aclService) {
        this.aclService = aclService;
    }

    /**
     * @param configService
     *            the configService to set
     */
    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @Autowired
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
    
}
