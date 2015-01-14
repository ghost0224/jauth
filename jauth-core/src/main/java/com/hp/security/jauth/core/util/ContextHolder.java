package com.hp.security.jauth.core.util;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hp.security.jauth.core.model.AuthLog;

public class ContextHolder {

	private static ThreadLocal<String> URL = new ThreadLocal<String>();
	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<ServletResponse> servletResponse = new ThreadLocal<ServletResponse>();
	private static ThreadLocal<Boolean> IsPassed = new ThreadLocal<Boolean>();
	private static ThreadLocal<AuthLog> log = new ThreadLocal<AuthLog>();
	private static ThreadLocal<String> authorizationCheck = new ThreadLocal<String>();
	
	static {
		ContextHolder.setPassed(true);
	}
	
	public static void setURL(String url) {
		URL.set(url);
    }

    public static String getURL() {
        return URL.get();
    }

    public static void clearURL() {
    	URL.remove();
    }
    
    public static void setAuthorizationCheck(String authorizationCheck) {
    	ContextHolder.authorizationCheck.set(authorizationCheck);
    }

    public static String getAuthorizationCheck() {
        return authorizationCheck.get();
    }

    public static void clearAuthorizationCheck() {
    	authorizationCheck.remove();
    }
    
    public static void setPassed(boolean isPassed) {
    	IsPassed.set(isPassed);
    }

    public static Boolean isPassed() {
        return IsPassed.get();
    }

    public static void clearIsPassed() {
    	IsPassed.remove();
    }

	public static HttpServletRequest getRequest() {
		return request.get();
	}

	public static void setRequest(HttpServletRequest request) {
		ContextHolder.request.set(request);
	}
	
	public static void clearRequest() {
		request.remove();
    }
	
	public static ServletResponse getServletResponse() {
		return servletResponse.get();
	}

	public static void setServletResponse(ServletResponse servletResponse) {
		ContextHolder.servletResponse.set(servletResponse);
	}
	
	public static void clearServletResponse() {
		servletResponse.remove();
    }

	public static void setLog(AuthLog authLog) {
		log.set(authLog);
    }

    public static AuthLog getLog() {
        return log.get();
    }

    public static void clearLog() {
    	log.remove();
    }
    
}
