/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.admin.util;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.admin.util.AdminUtil;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-admin-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class AdminUtilTest {

	private AdminUtil adminUtil;

	@Autowired
	public void setAdminUtil(AdminUtil adminUtil) {
		this.adminUtil = adminUtil;
	}
	
	@Test
	@Ignore
	public void setAllPermission() {
		List<String> results = adminUtil.setAllPermission(0);
		for(String r : results) {
			System.out.println(r);
		}
	}
   
}
