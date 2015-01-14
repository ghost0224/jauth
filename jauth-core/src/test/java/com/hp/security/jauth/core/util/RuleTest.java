/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
public class RuleTest {

    private String struts2_1 = "http://www.test.com/auth/test.action!goToPage?first=xvczxc&second=3dfe";
    private String struts2_2 = "http://www.test.com/test!goToPage?first=xvczxc&second=3dfe";
    private String struts2_3 = "http://www.test.com/auth/test!goToPage.do?first=xvczxc&second=3dfe";
    private String struts2_4 = "http://www.test.com/auth/test";
    private String spring_1 = "http://www.test.com/auth/demo/test/goToPage.do?first=xcvwe&second=teiio";
    private String spring_2 = "http://www.test.com/test.do/goToPage?first=xcvwe&second=teiio";
    private String spring_3 = "http://www.test.com/auth/test/goToPage?first=xcvwe&second=teiio";
    // String struts = "http://www.test.com/auth/test.do?operate=goToPage";

    private String struts2Pattern1 = "*.action!{authO}";
    private String struts2Pattern2 = "*!{authO}";
    private String struts2Pattern3 = "*!{authO}.do";
    private String struts2Pattern4 = "*/*";
    private String springPattern1 = "*/*/{authO}.do";
    private String springPattern2 = "*.do/{authO}";
//    private String springPattern3 = "*/{authO}";

    private RuleUtil ruleUtil;

    /**
     * @return the ruleUtil
     */
    public RuleUtil getRuleUtil() {
        return ruleUtil;
    }

    /**
     * @param ruleUtil
     *            the ruleUtil to set
     */
    @Autowired
    public void setRuleUtil(RuleUtil ruleUtil) {
        this.ruleUtil = ruleUtil;
    }
    
    @Test
	public void testAccessiblePage() {
		String s = "jauth-admin-example/login!loginPage";
		Assert.assertTrue(s.matches("[\\w\\W]*/login!loginPage"));
	}

    private void init() {
        RuleUtil.ruleMap = new HashMap<String, int[][]>();
        if (ruleUtil.validate(struts2Pattern1)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(struts2Pattern1), RuleUtil.analyse(struts2Pattern1));
        } else {
            System.out.println("failed to load rule: " + struts2Pattern1);
        }
        if (ruleUtil.validate(struts2Pattern2)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(struts2Pattern2), RuleUtil.analyse(struts2Pattern2));
        } else {
            System.out.println("failed to load rule: " + struts2Pattern2);
        }
        if (ruleUtil.validate(struts2Pattern3)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(struts2Pattern3), RuleUtil.analyse(struts2Pattern3));
        } else {
            System.out.println("failed to load rule: " + struts2Pattern2);
        }
        if (ruleUtil.validate(struts2Pattern4)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(struts2Pattern4), RuleUtil.analyse(struts2Pattern4));
        } else {
            System.out.println("failed to load rule: " + struts2Pattern2);
        }
        if (ruleUtil.validate(springPattern1)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(springPattern1), RuleUtil.analyse(springPattern1));
        } else {
            System.out.println("failed to load rule: " + springPattern1);
        }
        if (ruleUtil.validate(springPattern2)) {
            RuleUtil.ruleMap.put(ruleUtil.getKey(springPattern2), RuleUtil.analyse(springPattern2));
        } else {
            System.out.println("failed to load rule: " + springPattern2);
        }
//        if (ruleUtil.validate(springPattern3)) {
//            RuleUtil.ruleMap.put(ruleUtil.getKey(springPattern3), RuleUtil.analyse(springPattern3));
//        } else {
//            System.out.println("failed to load rule: " + springPattern3);
//        }
    }

    @Test
    public void testRule() {
        String module = "";
        String operation = "";
        init();
        List<String> testUrls = new ArrayList<String>();
        testUrls.add(struts2_4);
        for (String url : testUrls) {
            String formatStr = HttpUtil.getBusinessUrl(url);
            Map<String, String> result = ruleUtil.getControllerAndOperation(formatStr, null);
            module = result.get(Constants.AUTH_C);
            operation = result.get(Constants.AUTH_O);
            Assert.assertEquals(module, "auth/test");
            Assert.assertEquals(operation, "view");
            System.out.println("url:" + url + ", module:" + module + ", operation: " + operation);
        }
    }

}
