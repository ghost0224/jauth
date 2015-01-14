package com.hp.security.jauth.core.hook;

import javax.servlet.http.HttpServletRequest;

public interface SoapHook {

	String getServiceName(HttpServletRequest request);
	
	String getOperationName(HttpServletRequest request);
	
}
