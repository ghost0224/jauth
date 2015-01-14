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

import com.hp.security.jauth.core.model.Group;
import com.hp.security.jauth.core.model.GroupRole;
import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;

/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("group")
public class GroupController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        List<Group> groups = groupService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("groups", groups);
        model.addAttribute(groups);
        return super.baseReturn(request, response, root, "group");
    }

    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) Long groupId, HttpServletRequest request,
            HttpServletResponse response, Model model)
            throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        List<Role> roles = roleService.findAll();
        root.put("roles", roles);
        model.addAttribute(roles);
        if (null != groupId) {
            Group group = groupService.findById(groupId);
            root.put("group", group);
            model.addAttribute(group);
        }
        return super.baseReturn(request, response, root, "group_detail");
    }

    @RequestMapping("delete")
    public String delete(long groupId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, TemplateException {
        groupService.delete(groupId);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("update")
    public String update(Group group, @RequestParam(required = false) String roles,
            @RequestParam(required = false) String priority, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        if (null != roles && roles.length() > 0 && null != priority && priority.length() > 0) {
            String[] role = roles.split(",");
            String[] pri = priority.split(",");
            if (role.length == pri.length) {
                for (int i = 0; i < role.length; i++) {
                    GroupRole gr = new GroupRole();
                    gr.setGroup(group);
                    Role r = new Role();
                    r.setRoleId(Integer.parseInt(role[i]));
                    gr.setRole(r);
                    gr.setPriority(Integer.parseInt(pri[i]));
                    group.getGroupRoles().add(gr);
                }
            }
        }
        groupService.update(group);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("save")
    public String save(Group group, @RequestParam(required = false) String roles,
            @RequestParam(required = false) String priority, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        if (null != roles && roles.length() > 0 && null != priority && priority.length() > 0) {
            String[] role = roles.split(",");
            String[] pri = priority.split(",");
            if (role.length == pri.length) {
                for (int i = 0; i < role.length; i++) {
                    GroupRole gr = new GroupRole();
                    gr.setGroup(group);
                    Role r = new Role();
                    r.setRoleId(Integer.parseInt(role[i]));
                    gr.setRole(r);
                    gr.setPriority(Integer.parseInt(pri[i]));
                    group.getGroupRoles().add(gr);
                }
            }
        }
        groupService.save(group);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

}
