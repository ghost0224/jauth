package com.hp.security.jauth.core.model;

import java.io.Serializable;

public class Config implements Serializable {
 
	private static final long serialVersionUID = 2405385252978920132L;

	private String key;
	 
	private String value;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
	 
}
 
