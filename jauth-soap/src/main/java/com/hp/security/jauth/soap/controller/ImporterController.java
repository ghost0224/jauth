/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.soap.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.security.jauth.core.util.FreemarkerUtil;
import com.hp.security.jauth.soap.util.WsdlImporter;

import freemarker.template.TemplateException;

/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("importer")
public class ImporterController {
	
	private WsdlImporter wsdlImporter;
	private FreemarkerUtil freemarkerUtil;

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        return freemarkerUtil.baseReturn(request, response, root, "importer");
    }

    @RequestMapping("importWSDL")
    public String importWSDL(@RequestParam(required = true) String contextFilePath, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        if (null != contextFilePath) {
        	List<String> results = wsdlImporter.importControllerAndOperation(request, contextFilePath);
        	root.put("results", results);
        }
        return freemarkerUtil.baseReturn(request, response, root, "import_result");
    }

    @Autowired
	public void setWsdlImporter(WsdlImporter wsdlImporter) {
		this.wsdlImporter = wsdlImporter;
	}

    @Autowired
	public void setFreemarkerUtil(FreemarkerUtil freemarkerUtil) {
		this.freemarkerUtil = freemarkerUtil;
	}
    
}
