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

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.security.jauth.core.model.Application;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;

/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@org.springframework.stereotype.Controller
@RequestMapping("controller")
public class LogicalController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        List<Controller> controllers = controllerService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("controllers", controllers);
        model.addAttribute(controllers);
        return super.baseReturn(request, response, root, "controller");
    }

    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) Long controllerId, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        List<Application> applications = applicationService.findAll();
        root.put("apps", applications);
        if (null != controllerId) {
            Controller controller = controllerService.findById(controllerId);
            root.put("controller", controller);
            model.addAttribute(controller);
        }
        return super.baseReturn(request, response, root, "controller_detail");
    }

    @RequestMapping("delete")
    public String delete(long controllerId, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        controllerService.delete(controllerId);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("update")
    public String update(Controller controller, @RequestParam(required = false) long applicationId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, TemplateException {
    	if(applicationId > 0) {
    		Application app = new Application();
        	app.setApplicationId(applicationId);
        	controller.setApplication(app);
    	}
        controllerService.update(controller);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("save")
    public String save(Controller controller, @RequestParam(required = false) long applicationId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, TemplateException {
    	if(applicationId > 0) {
    		Application app = new Application();
        	app.setApplicationId(applicationId);
        	controller.setApplication(app);
    	}
        controllerService.save(controller);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

}
