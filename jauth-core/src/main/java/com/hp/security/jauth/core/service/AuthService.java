package com.hp.security.jauth.core.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.AuthLog;

public interface AuthService {
	
	String getFinalUserId(HttpServletRequest request, String sessionID);
	
	boolean getPermission(String authorizationCheck, HttpServletRequest request, ServletResponse response, Map<String, String> CO, String url, AuthLog authLog) throws IOException, ServletException;
	
	AssociateUser findAssociateUser(String userId);
	
	void recordLog(AuthLog authLog);

}
