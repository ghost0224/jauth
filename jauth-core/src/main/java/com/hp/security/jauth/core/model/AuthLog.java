package com.hp.security.jauth.core.model;

import java.io.Serializable;
import java.util.Date;

public class AuthLog implements Serializable {

	private static final long serialVersionUID = 6575748189578916497L;

	private long logId;
	
	private String userId;
	
	private String application = "";
	
	private String controller = "";
	
	private String operation = "";
	
	private Date insertDate;
	
	private String result = "AUTHENTICATION_FAILURE";
	
	private long startTime;
	
	private long jauthCost;
	
	private long overallCost;
	
	private String notes;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getJauthCost() {
		return jauthCost;
	}

	public void setJauthCost(long jauthCost) {
		this.jauthCost = jauthCost;
	}

	public long getOverallCost() {
		return overallCost;
	}

	public void setOverallCost(long overallCost) {
		this.overallCost = overallCost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return userId + "|" + application + "|" + controller + "|" + operation + "|" 
				+ insertDate + "|" + result + "|" + jauthCost + "|" + overallCost + "|" + notes;
	}
	
}
