package com.hp.security.jauth.core.hook;

import javax.servlet.http.HttpServletRequest;

import com.hp.security.jauth.core.model.AuthLog;

public interface AuthHook {
	
	boolean doProcess(HttpServletRequest request, AuthLog authLog);
	
	String getNotes(HttpServletRequest request);

}
