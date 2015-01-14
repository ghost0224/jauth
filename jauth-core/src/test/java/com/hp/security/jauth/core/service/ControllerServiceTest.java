/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.service.ControllerService;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class ControllerServiceTest {

    private ControllerService controllerService;

    @Autowired
    /**
     * @param controllerService the controllerService to set
     */
    public void setControllerService(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @Test
    public void testSave() {
        Controller controller = new Controller();
        controller.setActivate("Y");
        controller.setMapping("testAuth");
        controller.setModuleName("Test Auth");
        controller.setBusiness("Y");
        controllerService.save(controller);
    }

    @Test
    public void testFind() {
        List<Controller> controllers = controllerService.findAll();
        for (Controller c : controllers) {
            c.getOperations();
        }
    }

}
