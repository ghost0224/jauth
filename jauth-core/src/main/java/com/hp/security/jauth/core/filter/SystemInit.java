/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.hp.security.jauth.core.hook.SoapHook;
import com.hp.security.jauth.core.hook.UserValidationHook;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.service.ConfigService;
import com.hp.security.jauth.core.service.ControllerService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.MessageContext;
import com.hp.security.jauth.core.util.RuleUtil;

/**
 * @author huangyiq
 *
 */
@Service
public class SystemInit implements InitializingBean, ServletContextAware {

    private ServletContext servletContext;
    private RuleUtil ruleUtil;
    private ControllerService controllerService;
    private ConfigService configService;
    public static List<Controller> allControllerList;
    public static Map<String, Controller> allControllerMappingMap = new HashMap<String, Controller>();
    public static Map<Long, Controller> allControllerIdMap = new HashMap<Long, Controller>();
    public UserValidationHook userValidationHook;
    public SoapHook soapHook;
    private Logger log = Logger.getLogger(this.getClass().getName());
    
    //config
    public static String authActive;
    public static String authorizationCheck;
    public static String arithmeticIndex;
    public static String sessionID;
    public static String sessionACL;
    public static String rules;
    public static String accessiblePage;
    public static String accessibleHost;
    public static String exceptionPage;
    public static String loginPage;
    public static String soapEnabled;
    public static String protectWSDL;
    public static String dbLogEnabled;
    public static String logPath;
    
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void afterPropertiesSet() throws Exception {
    	ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        initMessageProperties(resolver);
        ruleUtil.initRules();
        log.info("initialized jauth rules.");
        List<Controller> controllerList = controllerService.findAll();
        for (Controller c : controllerList) {
        	if(null != c.getApplication()) {
        		c.setMapping(c.getApplication().getMapping() + "/" + c.getMapping());
        	}
        	SystemInit.allControllerMappingMap.put(c.getMapping(), c);
        	SystemInit.allControllerIdMap.put(c.getControllerId(), c);
        }
        log.info("loaded controllers.");
        SystemInit.allControllerList = controllerList;
    }
    
    public void initMessageProperties(ResourcePatternResolver resolver) throws IOException {
    	//finding system messages.
    	Resource jauthCoreMessages = resolver.getResource("jauth-core-message.properties");
        Properties jauthCoreProp = new Properties();
        jauthCoreProp.load(jauthCoreMessages.getInputStream());
        Set<Object> jauthCoreKeys = jauthCoreProp.keySet();
        for(Object k : jauthCoreKeys) {
        	String key = k.toString();
        	String value = jauthCoreProp.get(key).toString();
        	MessageContext.messages.put(key, value);
        }
        log.info("loaded jauth core messages.");
        
        try {
	        Resource jauthAdminMessages = resolver.getResource("jauth-admin-message.properties");
	        Properties jauthAdminProp = new Properties();
	        jauthAdminProp.load(jauthAdminMessages.getInputStream());
	        Set<Object> jauthAdminKeys = jauthAdminProp.keySet();
	        for(Object k : jauthAdminKeys) {
	        	String key = k.toString();
	        	String value = jauthAdminProp.get(key).toString();
	        	MessageContext.messages.put(key, value);
	        }
	        log.info("loaded jauth admin messages.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find any jauth admin messages.");
        }
        
        try {
	        Resource jauthSoapMessages = resolver.getResource("jauth-soap-message.properties");
	        Properties jauthSoapProp = new Properties();
	        jauthSoapProp.load(jauthSoapMessages.getInputStream());
	        Set<Object> jauthSoapKeys = jauthSoapProp.keySet();
	        for(Object k : jauthSoapKeys) {
	        	String key = k.toString();
	        	String value = jauthSoapProp.get(key).toString();
	        	MessageContext.messages.put(key, value);
	        }
	        log.info("loaded jauth soap messages.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find any jauth soap messages.");
        }
        
    	//finding custom messages.
        try {
        	Resource customMessages = resolver.getResource("message.properties");
        	Properties customProp = new Properties();
        	customProp.load(customMessages.getInputStream());
            Set<Object> customKeys = customProp.keySet();
            for(Object k : customKeys) {
            	String key = k.toString();
            	String value = customProp.get(key).toString();
            	MessageContext.messages.put(key, value);
            }
            log.info("loaded jauth custom messages.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find any jauth custom messages.");
        }
        
        //finding jauth properties
        try {
        	Resource jauth = resolver.getResource("jauth.properties");
        	Properties jauthProp = new Properties();
        	jauthProp.load(jauth.getInputStream());
        	authActive = jauthProp.get(Constants.CONFIG_KEY_ACTIVE) == null ? configService.findByKey(Constants.CONFIG_KEY_ACTIVE).getValue() : jauthProp.get(Constants.CONFIG_KEY_ACTIVE).toString();
        	authorizationCheck = jauthProp.get(Constants.CONFIG_AUTHORIZATION_CHECK) == null ? configService.findByKey(Constants.CONFIG_AUTHORIZATION_CHECK).getValue() : jauthProp.get(Constants.CONFIG_AUTHORIZATION_CHECK).toString();
        	arithmeticIndex = jauthProp.get(Constants.CONFIG_KEY_ARITHMETIC) == null ? configService.findByKey(Constants.CONFIG_KEY_ARITHMETIC).getValue() : jauthProp.get(Constants.CONFIG_KEY_ARITHMETIC).toString();
        	sessionID = jauthProp.get(Constants.CONFIG_KEY_SESSION_ID) == null ? configService.findByKey(Constants.CONFIG_KEY_SESSION_ID).getValue() : jauthProp.get(Constants.CONFIG_KEY_SESSION_ID).toString();
        	sessionACL = jauthProp.get(Constants.CONFIG_KEY_SESSION_ACL) == null ? configService.findByKey(Constants.CONFIG_KEY_SESSION_ACL).getValue() : jauthProp.get(Constants.CONFIG_KEY_SESSION_ACL).toString();
        	rules = jauthProp.get(Constants.CONFIG_KEY_RULES) == null ? configService.findByKey(Constants.CONFIG_KEY_RULES).getValue() : jauthProp.get(Constants.CONFIG_KEY_RULES).toString();
        	accessiblePage = jauthProp.get(Constants.CONFIG_KEY_ACCESSIBLE_PAGE) == null ? configService.findByKey(Constants.CONFIG_KEY_ACCESSIBLE_PAGE).getValue() : jauthProp.get(Constants.CONFIG_KEY_ACCESSIBLE_PAGE).toString();
        	accessibleHost = jauthProp.get(Constants.CONFIG_KEY_ACCESSIBLE_HOST) == null ? configService.findByKey(Constants.CONFIG_KEY_ACCESSIBLE_HOST).getValue() : jauthProp.get(Constants.CONFIG_KEY_ACCESSIBLE_HOST).toString();
        	exceptionPage = jauthProp.get(Constants.CONFIG_KEY_EXCEPTION) == null ? configService.findByKey(Constants.CONFIG_KEY_EXCEPTION).getValue() : jauthProp.get(Constants.CONFIG_KEY_EXCEPTION).toString();
        	loginPage = jauthProp.get(Constants.CONFIG_KEY_LOGIN_PAGE) == null ? configService.findByKey(Constants.CONFIG_KEY_LOGIN_PAGE).getValue() : jauthProp.get(Constants.CONFIG_KEY_LOGIN_PAGE).toString();
        	soapEnabled = jauthProp.get(Constants.CONFIG_KEY_SOAP_ENABLED) == null ? configService.findByKey(Constants.CONFIG_KEY_SOAP_ENABLED).getValue() : jauthProp.get(Constants.CONFIG_KEY_SOAP_ENABLED).toString();
        	protectWSDL = jauthProp.get(Constants.CONFIG_KEY_PROTECT_WSDL) == null ? configService.findByKey(Constants.CONFIG_KEY_PROTECT_WSDL).getValue() : jauthProp.get(Constants.CONFIG_KEY_PROTECT_WSDL).toString();
        	dbLogEnabled = jauthProp.get(Constants.CONFIG_KEY_DB_LOG_ENABLED) == null ? configService.findByKey(Constants.CONFIG_KEY_DB_LOG_ENABLED).getValue() : jauthProp.get(Constants.CONFIG_KEY_DB_LOG_ENABLED).toString();
        	logPath = jauthProp.get(Constants.CONFIG_KEY_LOG_PATH) == null ? configService.findByKey(Constants.CONFIG_KEY_LOG_PATH).getValue() : jauthProp.get(Constants.CONFIG_KEY_LOG_PATH).toString();
        	if(logPath.length() > 0) {
        		File logDir = new File(logPath);
        		if(!logDir.exists()) {
        			logDir.mkdirs();
        		}
        	}
            log.info("loaded jauth.properties.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find jauth.properties.");
        }
    }
    
    /**
     * @param ruleUtil
     *            the ruleUtil to set
     */
    @Autowired
    public void setRuleUtil(RuleUtil ruleUtil) {
        this.ruleUtil = ruleUtil;
    }

    /**
     * @param controllerService
     *            the controllerService to set
     */
    @Autowired
    public void setControllerService(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
    
}
