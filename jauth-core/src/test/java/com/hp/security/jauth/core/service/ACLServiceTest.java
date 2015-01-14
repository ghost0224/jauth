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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.model.ACL;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class ACLServiceTest {

    private ACLDao aclDao;
    private ACLService aclService;
    private AuthLogService authLogService;
    @Value("${databaseType}")
    private String databaseType;
    
    /**
     * @param aclDao
     *            the aclDao to set
     */
    @Autowired
    public void setAclDao(ACLDao aclDao) {
        this.aclDao = aclDao;
    }
    
    @Autowired
    public void setAclService(ACLService aclService) {
		this.aclService = aclService;
	}
    
    @Autowired
	public void setAuthLogService(AuthLogService authLogService) {
		this.authLogService = authLogService;
	}

	@Test
    public void testFind() {
		if(databaseType.equals("mysql")) {
			List<ACL> acls = aclDao.findByPrincipal(1, 0);
	        Assert.assertTrue(acls.size() > 0);
		} else if(databaseType.equals("oracle")) {
	        List<ACL> acls = aclDao.findByPrincipal(0, 0);
	        Assert.assertTrue(acls.size() > 0);
		}
    }
	
	@Test
	public void testFindDetailStatusByPrincipal() {
		if(databaseType.equals("mysql")) {
			Assert.assertTrue(aclService.findDetailStatusByPrincipal(1, 0).size() > 0);
			Assert.assertTrue(aclService.findDetailStatusByPrincipalAndController(1, 0, 1).size()>0);
		} else if(databaseType.equals("oracle")) {
			Assert.assertTrue(aclService.findDetailStatusByPrincipal(0, 0).size() > 0);
			Assert.assertTrue(aclService.findDetailStatusByPrincipalAndController(0, 0, 1).size()>0);
		}
	}

}
