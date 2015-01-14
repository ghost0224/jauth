/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.dao.OperationDao;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.service.OperationService;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class OperationServiceTest {

    private OperationDao operationDao;
    private OperationService operationService;

    /**
     * @param operationDao
     *            the operationDao to set
     */
    @Autowired
    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * @param operationService
     *            the operationService to set
     */
    @Autowired
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }


    @Test
    @Ignore
    public void testSave() {
        Operation operation = new Operation();
        Controller c = new Controller();
        c.setControllerId(3l);
        operation.setController(c);
        operation.setOperationId(0);
        operation.setName("viewPage");
        operationDao.save(operation);
    }

    @Test
    public void testFind() {
//        Operation on = operationService.findById(0, 0);
        List<Operation> operations = operationService.findByControllerId(3);
        for (Operation o : operations) {
            o.getController();
        }
    }

}
