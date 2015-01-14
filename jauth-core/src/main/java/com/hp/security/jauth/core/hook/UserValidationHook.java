package com.hp.security.jauth.core.hook;

public interface UserValidationHook {

	boolean validation(String username, String password);
	
}
