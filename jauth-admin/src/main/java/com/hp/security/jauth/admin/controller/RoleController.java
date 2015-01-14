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

import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        List<Role> roles = roleService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("roles", roles);
        model.addAttribute(roles);
        return super.baseReturn(request, response, root, "role");
    }

    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) Long roleId, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        if (null != roleId) {
            Role role = roleService.findById(roleId);
            root.put("role", role);
            model.addAttribute(role);
        }
        return super.baseReturn(request, response, root, "role_detail");
    }

    @RequestMapping("delete")
    public String delete(long roleId, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        roleService.delete(roleId);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("update")
    public String update(Role role, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        roleService.update(role);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("save")
    public String save(Role role, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        roleService.save(role);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

}
