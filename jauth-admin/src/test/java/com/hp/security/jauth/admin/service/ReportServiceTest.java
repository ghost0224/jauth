/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.admin.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-admin-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class ReportServiceTest {

    private ReportService reportService;

    @Resource(name="reportService")
    public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
    
    @Test
    public void testPieWithModule() {
    	Assert.assertTrue(reportService.pieWithModule(false).size() > 0);
    }
    
    @Test
    public void testTrendLineWithAccess() {
    	Assert.assertTrue(reportService.trendLineWithAccess(false).size() > 0);
    }

}
