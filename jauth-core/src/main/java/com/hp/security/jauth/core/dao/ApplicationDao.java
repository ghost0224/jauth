package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.Application;

public interface ApplicationDao {

	Application findById(long applicationId);
	
	Application findByMapping(String mapping);
	
	List<Application> findAll();
	
	void save(Application application);
	
	void update(Application application);
	
	void delete(long applicationId);
	
}
