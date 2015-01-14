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

import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;

/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@org.springframework.stereotype.Controller
@RequestMapping("ACL")
public class ACLController extends BaseController {

    @RequestMapping("view")
    public String view(long principalId, int principalType, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        AssociateUser user = null;
        Role role = null;
        Map<String, Object> root = new HashMap<String, Object>();
        if (principalType == 0) {
            role = roleService.findById(principalId);
            root.put("role", role);
            model.addAttribute(role);
        } else if (principalType == 1) {
            user = associateUserService.findById(principalId);
            root.put("user", user);
            model.addAttribute(user);
        }
        root.put("principalId", principalId);
        root.put("principalType", principalType);
        List<Controller> controllers = controllerService.findAll();
        root.put("controllers", controllers);
        model.addAttribute(principalId);
        model.addAttribute(principalType);
        model.addAttribute(controllers);
        return super.baseReturn(request, response, root, "acl");
    }

    @RequestMapping("operationList")
    public String operationList(long principalId, int principalType, long controllerId, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("principalId", principalId);
        root.put("principalType", principalType);
        root.put("controllerId", controllerId);
        List<Operation> operations = operationService.findByControllerId(controllerId);
        root.put("operations", operations);
        ACL acl = aclService.findByPrincipalAndController(principalId, principalType, controllerId);
        root.put("acl", acl);
        model.addAttribute(principalId);
        model.addAttribute(principalType);
        model.addAttribute(controllerId);
        model.addAttribute(operations);
        if(null != acl)
        	model.addAttribute(acl);
        return super.baseReturn(request, response, root, "operation_list");
    }

    @RequestMapping("saveOrUpdate")
    public void saveOrUpdate(String active, long principalId, int principalType, long controllerId, int operationSize,
            String operations, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        if (active == null || !active.equals("true")) {
            aclService.delete(principalId, principalType, controllerId);
        } else {
            aclService.saveOrUpdate(principalId, principalType, controllerId, operationSize, operations);
        }
        model.addAttribute(Constants.SUCCESS);
        operationList(principalId, principalType, controllerId, request, response, model);
    }

}
