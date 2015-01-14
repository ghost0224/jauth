package com.hp.security.jauth.core.model;

import java.io.Serializable;

public class Application implements Serializable {

	private static final long serialVersionUID = 5377594071766340006L;
	
	private long applicationId;
	
	private String mapping;
	
	private String applicationName;

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
}
