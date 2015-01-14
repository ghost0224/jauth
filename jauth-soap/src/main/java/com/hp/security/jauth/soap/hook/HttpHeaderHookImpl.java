package com.hp.security.jauth.soap.hook;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.hook.AuthHook;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.service.AssociateUserService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.HttpUtil;
import com.hp.security.jauth.core.util.MD5;
import com.hp.security.jauth.core.util.MessageContext;

//@Service("authHook")
public class HttpHeaderHookImpl implements AuthHook {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AssociateUserService userService;
    private SystemInit systemInit;
    private String username;
    private String password;
    private String email;
	
    @Autowired
	public void setUserService(AssociateUserService userService) {
		this.userService = userService;
	}
	
    @Autowired
	public void setSystemInit(SystemInit systemInit) {
		this.systemInit = systemInit;
	}
	
	public boolean doProcess(HttpServletRequest request, AuthLog authLog) {
        String username = null, password = null, email = null;
    	/* get from http header */
    	username = request.getHeader(username);
    	password = request.getHeader(password);
    	email = request.getHeader(email);
        if(null != authLog) {
			authLog.setUserId(username);
			authLog.setController("N/A");
			authLog.setOperation("N/A");
			authLog.setNotes(email + "@" + HttpUtil.getRemoteIpAddr(request));
		}
        String msg = String.format("{applicationId:%s}, {accessToken:%s}, {operatorEmail:%s}", username, password, email);
        logger.debug("doProcess() - http/soap header = {}", msg);
            
		if(null == username || null == password) {
			String message = MessageContext.getMessage("AUTH_ERROR_101");
			if(null != authLog) {
				authLog.setResult(Constants.AUTHENTICATION_FAILURE);
			}
			throw new AuthException("AUTH_ERROR_101", message);
		}
		
		if(null == systemInit.userValidationHook) {
			AssociateUser user = userService.findByUserId(username);
			if(null != user && MD5.getInstance().getMD5ofStr(password).equalsIgnoreCase(user.getPassword())) {
				// here the user id come from soap header, force it to be available into session.
	            request.getSession().setAttribute(SystemInit.sessionID, username);
	            request.getSession().setAttribute(email, email);
				return true;
			} else {
				String message = MessageContext.getMessage("AUTH_ERROR_102");
				if(null != authLog) {
					authLog.setResult(Constants.AUTHENTICATION_FAILURE);
				}
				throw new AuthException("AUTH_ERROR_102", message);
			}
		} else {
			if(systemInit.userValidationHook.validation(username, password)) {
				return true;
			} else {
				String message = MessageContext.getMessage("AUTH_ERROR_102");
				if(null != authLog) {
					authLog.setResult(Constants.AUTHENTICATION_FAILURE);
				}
				throw new AuthException("AUTH_ERROR_102", message);
			}
		}
		
	}

	public String getNotes(HttpServletRequest request) {
		Object emailObject = request.getSession().getAttribute(email);
		String notes = "";
		if(null != emailObject) {
			notes += (String)emailObject;
		}
		notes += "@" + HttpUtil.getRemoteIpAddr(request);
        return notes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
