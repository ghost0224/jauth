/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.exception;


/**
 * @author huangyiq
 *
 */
public class AuthException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;
    private String code;

    public AuthException() {
        super();
    }

    public AuthException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public AuthException(String code, String message) {
        super(message);
        this.code = code;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
