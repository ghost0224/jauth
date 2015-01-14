package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ApplicationDao;
import com.hp.security.jauth.core.model.Application;
import com.hp.security.jauth.core.service.ApplicationService;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {
	
	private ApplicationDao applicationDao;
	
	@Autowired
	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public Application findById(long applicationId) {
		return applicationDao.findById(applicationId);
	}

	public List<Application> findAll() {
		return applicationDao.findAll();
	}

	public void save(Application application) {
		applicationDao.save(application);
	}

	public void update(Application application) {
		applicationDao.update(application);
	}

	public void delete(long applicationId) {
		applicationDao.delete(applicationId);
	}

	public Application findByMapping(String mapping) {
		return applicationDao.findByMapping(mapping);
	}

}
