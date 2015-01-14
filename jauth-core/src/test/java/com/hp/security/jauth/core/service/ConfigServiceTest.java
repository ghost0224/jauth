/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.model.AssociateUser;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class ConfigServiceTest {

    private AssociateUserService associateUserService;

    public AssociateUserService getAssociateUserService() {
		return associateUserService;
	}

    @Autowired
	public void setAssociateUserService(AssociateUserService associateUserService) {
		this.associateUserService = associateUserService;
	}

	@Test
    public void testFind() {
		List<AssociateUser> users = associateUserService.findAll();
		Assert.assertTrue(users.size()>0);
    }

}
