/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.security.jauth.core.model.Application;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("application")
public class ApplicationController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        List<Application> apps = applicationService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("apps", apps);
        return super.baseReturn(request, response, root, "application");
    }

    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) Long applicationId, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        if (null != applicationId) {
            Application app = applicationService.findById(applicationId);
            root.put("app", app);
        }
        return super.baseReturn(request, response, root, "application_detail");
    }

    @RequestMapping("delete")
    public String delete(long applicationId, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        applicationService.delete(applicationId);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("update")
    public String update(Application application, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
    	applicationService.update(application);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("save")
    public String save(Application application, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
    	applicationService.save(application);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

}
