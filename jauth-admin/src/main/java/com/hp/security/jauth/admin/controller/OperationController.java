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

import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@org.springframework.stereotype.Controller
@RequestMapping("operation")
public class OperationController extends BaseController {

    @RequestMapping("view")
    public String view(long controllerId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        List<Operation> operations = operationService.findByControllerId(controllerId);
        root.put("operations", operations);
        model.addAttribute(operations);
        Controller controller = controllerService.findById(controllerId);
        root.put("controller", controller);
        model.addAttribute(controller);
        return super.baseReturn(request, response, root, "operation");
    }

    @RequestMapping("detail")
    public String detail(long controllerId,
            @RequestParam(required = false) Integer operationId, HttpServletRequest request,
            HttpServletResponse response, Model model)
            throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("controllerId", controllerId);
        model.addAttribute(controllerId);
        if (null != operationId) {
            Operation operation = operationService.findById(controllerId, operationId);
            root.put("operation", operation);
            model.addAttribute(operation);
        }
        return super.baseReturn(request, response, root, "operation_detail");
    }

    @RequestMapping("delete")
    public String delete(long controllerId, int operationId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        operationService.delete(controllerId, operationId);
        model.addAttribute(Constants.SUCCESS);
        return view(controllerId, request, response, model);
    }

    @RequestMapping("update")
    public String update(long controllerId, Operation operation, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        Controller controller = new Controller();
        controller.setControllerId(controllerId);
        operation.setController(controller);
        operationService.update(operation);
        model.addAttribute(Constants.SUCCESS);
        return view(controllerId, request, response, model);
    }

    @RequestMapping("save")
    public String save(long controllerId, Operation operation, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        Controller controller = new Controller();
        controller.setControllerId(controllerId);
        operation.setController(controller);
        operationService.save(operation);
        model.addAttribute(Constants.SUCCESS);
        return view(controllerId, request, response, model);
    }

}
