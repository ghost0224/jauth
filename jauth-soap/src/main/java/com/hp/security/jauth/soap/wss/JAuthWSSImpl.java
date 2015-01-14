package com.hp.security.jauth.soap.wss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.service.AssociateUserService;
import com.hp.security.jauth.core.service.AuthService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.ContextHolder;
import com.hp.security.jauth.core.util.HttpUtil;
import com.hp.security.jauth.core.util.MD5;
import com.hp.security.jauth.core.util.MessageContext;

public class JAuthWSSImpl implements CallbackHandler {
	
	private AuthService authService;
	private AssociateUserService userService;
	private SystemInit systemInit;
	
	@Autowired
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
	@Autowired
	public void setUserService(AssociateUserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setSystemInit(SystemInit systemInit) {
		this.systemInit = systemInit;
	}
	
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		if(!ContextHolder.isPassed()) {
			HttpServletRequest request = ContextHolder.getRequest();
			ServletResponse servletResponse = ContextHolder.getServletResponse();
			String url = ContextHolder.getURL();
			AuthLog authLog = ContextHolder.getLog();
			String authorizationCheck = ContextHolder.getAuthorizationCheck();
			authLog.setNotes("@" + HttpUtil.getRemoteIpAddr(request));
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
			String username = pc.getIdentifier();
			String password = pc.getPassword();
			
			if(null == username || null == password) {
				String message = MessageContext.getMessage("AUTH_ERROR_101");
				if(null != authLog) {
					authLog.setResult(Constants.AUTHENTICATION_FAILURE);
				}
				throw new AuthException("AUTH_ERROR_101", message);
			}
			
			boolean result = false;
			if(null == systemInit.userValidationHook) {
				AssociateUser user = userService.findByUserId(username);
				if(null != user && MD5.getInstance().getMD5ofStr(password).equalsIgnoreCase(user.getPassword())) {
					result = true;
				}
			} else {
				result = systemInit.userValidationHook.validation(username, password);
			}
			authLog.setUserId(username);
			if(result) {
				request.getSession().setAttribute(SystemInit.sessionID, username);
				Map<String, String> CO = new HashMap<String, String>();
				try {
					result = authService.getPermission(authorizationCheck, request, servletResponse, CO, url, authLog);
				} catch (ServletException e) {
					result = false;
					e.printStackTrace();
				}
				if("Y".equalsIgnoreCase(authorizationCheck)) {
					if(!result) {
						String message = MessageContext.getMessage("AUTH_ERROR_10", new String[]{"user",authLog.getUserId()}, new String[]{"controller", CO.get(Constants.AUTH_C)}, new String[]{"operation",CO.get(Constants.AUTH_O)});
		                throw new AuthException("AUTH_ERROR_10", message);
					}
				}
			} else {
				String message = MessageContext.getMessage("AUTH_ERROR_102");
				if(null != authLog) {
					authLog.setResult(Constants.AUTHENTICATION_FAILURE);
				}
				throw new AuthException("AUTH_ERROR_102", message);
			}
		}
	}

}
