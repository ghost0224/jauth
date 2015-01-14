/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.model.AuthLog;
import com.hp.security.jauth.core.util.Constants;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class AuthLogServiceTest {

    private AuthLogService authLogService;

	public AuthLogService getAuthLogService() {
		return authLogService;
	}

	@Autowired
	public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}

	@Test
    public void testFind() {
		AuthLog authLog = new AuthLog();
		authLog.setApplication("/a");
		authLog.setUserId("AuthAdmin");
		authLog.setController("c");
		authLog.setOperation("o");
		authLog.setJauthCost(30);
		authLog.setOverallCost(50);
		authLog.setInsertDate(new Date());
		authLog.setResult(Constants.SUCCESS);
		authLogService.save(authLog);
    }

}
