package com.hp.security.jauth.soap.util;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class WSDLImporterTest {

	private WsdlImporter importUtil;

	@Autowired
	public void setImportUtil(WsdlImporter importUtil) {
		this.importUtil = importUtil;
	}
	
	@Test
	@Ignore
	public void testImportControllerAndOperation() {
		List<String> result = importUtil.importControllerAndOperation(null, "/applicationContext.xml");
		for(String r : result) {
			System.out.println(r);
		}
	}
	
}
