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

import com.hp.security.jauth.core.model.Application;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class ApplicationServiceTest {

    private ApplicationService applicationService;

    public ApplicationService getApplicationService() {
		return applicationService;
	}

    @Autowired
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Test
    public void testSave() {
        Application app = new Application();
        app.setApplicationName("myApp");
        app.setMapping("my-app");
        applicationService.save(app);
        Application appSaved = applicationService.findById(app.getApplicationId());
        Assert.assertTrue(appSaved.getMapping().equals("my-app"));
    }

    @Test
    public void testUpdate() {
    	Application app = new Application();
        app.setApplicationName("myApp");
        app.setMapping("my-app");
        applicationService.save(app);
        Application appSaved = applicationService.findByMapping("my-app");
        appSaved.setApplicationName("yourApp");
        appSaved.setMapping("your-app");
        applicationService.update(appSaved);
    }

    @Test
    public void testDelete() {
    	Application app = new Application();
        app.setApplicationName("myApp");
        app.setMapping("my-app");
        applicationService.save(app);
        applicationService.delete(app.getApplicationId());
    }

    @Test
    public void testFind() {
    	Application app = new Application();
        app.setApplicationName("myApp");
        app.setMapping("my-app");
        applicationService.save(app);
        List<Application> apps = applicationService.findAll();
        Assert.assertTrue(apps.size()>0);
    }

}
