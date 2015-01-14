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

import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.Group;
import com.hp.security.jauth.core.model.UserGroup;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.MD5;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 *	yiqingh@hp.com
 */
@Controller
@RequestMapping("user")
public class AssociateUserController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, TemplateException {
        List<AssociateUser> users = associateUserService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("users", users);
        model.addAttribute(users);
        return baseReturn(request, response, root, "user");
    }
    
    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) Long associateUserId, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        List<Group> groups = groupService.findAll();
        root.put("groups", groups);
        if (null != associateUserId) {
            AssociateUser user = associateUserService.findById(associateUserId);
            root.put("user", user);
            model.addAttribute(user);
        }
        return baseReturn(request, response, root, "user_detail");
    }

    @RequestMapping("delete")
    public String delete(long associateUserId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
        associateUserService.delete(associateUserId);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("update")
    public String update(AssociateUser user, String prevPassword, @RequestParam(required = false) String groups,
            @RequestParam(required = false) String priority, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
    	if(null != user.getPassword() && user.getPassword().trim().length() > 0) {
    		user.setPassword(MD5.getInstance().getMD5ofStr(user.getPassword()));
    	} else {
    		user.setPassword(prevPassword);
    	}
        if (null != groups && groups.length() > 0 && null != priority && priority.length() > 0) {
            String[] group = groups.split(",");
            String[] pri = priority.split(",");
            if (group.length == pri.length) {
                for (int i = 0; i < group.length; i++) {
                    UserGroup ug = new UserGroup();
                    ug.setUser(user);
                    Group g = new Group();
                    g.setGroupId(Integer.parseInt(group[i]));
                    ug.setGroup(g);
                    ug.setPriority(Integer.parseInt(pri[i]));
                    user.getUserGroups().add(ug);
                }
            }
        }
        associateUserService.update(user);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

    @RequestMapping("save")
    public String save(AssociateUser user, @RequestParam(required = false) String groups,
            @RequestParam(required = false) String priority, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException,
            TemplateException {
    	if(null != user.getPassword() && user.getPassword().trim().length() > 0) {
    		user.setPassword(MD5.getInstance().getMD5ofStr(user.getPassword()));
    	}
        if (null != groups && groups.length() > 0 && null != priority && priority.length() > 0) {
            String[] group = groups.split(",");
            String[] pri = priority.split(",");
            if (group.length == pri.length) {
                for (int i = 0; i < group.length; i++) {
                    UserGroup ug = new UserGroup();
                    ug.setUser(user);
                    Group g = new Group();
                    g.setGroupId(Integer.parseInt(group[i]));
                    ug.setGroup(g);
                    ug.setPriority(Integer.parseInt(pri[i]));
                    user.getUserGroups().add(ug);
                }
            }
        }
        associateUserService.save(user);
        model.addAttribute(Constants.SUCCESS);
        return view(request, response, model);
    }

}
