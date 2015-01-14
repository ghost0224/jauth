package com.hp.security.jauth.core.dao;

import com.hp.security.jauth.core.model.AuthLog;

public interface AuthLogDao {
	
	void save(AuthLog authLog);
	
}
