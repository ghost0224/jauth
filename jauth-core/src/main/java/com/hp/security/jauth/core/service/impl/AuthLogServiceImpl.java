package com.hp.security.jauth.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.AuthLogDao;
import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.service.AuthLogService;

@Service("authLogService")
public class AuthLogServiceImpl implements AuthLogService {
	
	private AuthLogDao authLogDao;
	
	@Autowired
	public void setAuthLogDao(AuthLogDao authLogDao) {
		this.authLogDao = authLogDao;
	}

	public void save(AuthLog authLog) {
		authLogDao.save(authLog);
	}

}
