package com.hp.security.jauth.soap.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.model.Application;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.service.ApplicationService;
import com.hp.security.jauth.core.service.ControllerService;
import com.hp.security.jauth.core.service.OperationService;
import com.hp.security.jauth.core.util.HttpUtil;

@Service
public class WsdlImporter {
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	private String wsdlPrefix = "services";
	private ApplicationService applicationService;
	private ControllerService controllerService;
	private OperationService operationService;

	public List<String> importControllerAndOperation(HttpServletRequest request, String contextPath) {
		List<String> results = new ArrayList<String>();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources;
		try {
			resources = resolver.getResources("classpath:" + contextPath);
			if(resources.length > 0) {
				Element root = SoapUtil.getRoot(resources[0].getInputStream());
				if (null != root) {
					List<Element> children = root.elements();
					for (Element c : children) {
						if (c.getName().equalsIgnoreCase("endpoint")) {
							List<String> wsdlList = new ArrayList<String>();
							String bathPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
							String appMapping = request.getContextPath().replaceAll("/", "");
							String controllerMapping = wsdlPrefix + c.attributeValue("address");
							String wsdlLink = bathPath + controllerMapping + "?wsdl";
							wsdlList.add(wsdlLink);
							saveControllerAndOperation(appMapping, controllerMapping, wsdlList, results);
						}
					}
					results.add("Done!");
				}
			} else {
				results.add("File not found!");
			}
		} catch (IOException e) {
			results.add("File not found!");
			log.error(e);
		}
		return results;
	}
	
	private void saveControllerAndOperation(String appMapping, String controllerMapping, List<String> wsdlList, List<String> results) {
		try {
			for(String wsdlLink : wsdlList) {
				String wsdl = HttpUtil.sendGet(wsdlLink, null);
				InputStream in = new ByteArrayInputStream(wsdl.getBytes());
				Element root = SoapUtil.getRoot(in);
				if (null != root) {
					// get mapping
					Application app = applicationService.findByMapping(appMapping);
					// get name
					String controllerName = controllerMapping.substring(controllerMapping.indexOf("/")+1);
					// define current controller
					Controller controller = new Controller();
					controller.setActivate("Y");
					controller.setBusiness("Y");
					controller.setMapping(controllerMapping);
					controller.setModuleName(controllerName);
					controller.setApplication(app);
					// get operations
					List<Element> children = root.elements();
					for (Element c : children) {
						if (c.getName().equalsIgnoreCase("binding")) {
							List<Element> operations = c.elements();
							for(Element o : operations) {
								if(o.getName().equalsIgnoreCase("operation")) {
									String operationName = o.attributeValue("name");
									Operation operation = new Operation();
									operation.setController(controller);
									operation.setName(operationName);
									controller.getOperations().add(operation);
								}
							}
						}
					}
					// find it in db
					Controller contro = controllerService.findByMapping(controller.getMapping());
					if(null == contro) {
						// save controller
						controllerService.save(controller);
						String tempR = "";
						// save operations
						for(Operation o : controller.getOperations()) {
							operationService.save(o);
							tempR += o.getName() + "|";
						}
						// build result
						String result = "Create - Service: " + controller.getModuleName() + ", Operateions: " + tempR;
						results.add(result);
					} else {
						String tempR = "";
						// find operations of current controller
						List<Operation> databaseOperations = operationService.findByControllerId(contro.getControllerId());
						for(Operation o : controller.getOperations()) {
							boolean createFlag = true;
							// if current operation exist, don't create
							for(Operation dbo : databaseOperations) {
								if(o.getName().equals(dbo.getName())) {
									createFlag = false;
								}
							}
							// create operation
							if(createFlag) {
								o.setController(contro);
								operationService.save(o);
								tempR += o.getName() + "|";
							}
						}
						if(tempR.length() > 0) {
							// build result
							String result = "Update - Service: " + contro.getModuleName() + ", Operateions: " + tempR;
							results.add(result);
						}
					}
				}
			}
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	@Autowired
	public void setControllerService(ControllerService controllerService) {
		this.controllerService = controllerService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
}
