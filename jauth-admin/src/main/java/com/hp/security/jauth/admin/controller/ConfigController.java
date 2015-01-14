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

import com.hp.security.jauth.core.model.Config;
import com.hp.security.jauth.core.util.Constants;

import freemarker.template.TemplateException;

/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("config")
public class ConfigController extends BaseController {

    @RequestMapping("view")
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        List<Config> configs = configService.findAll();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("configs", configs);
        model.addAttribute(configs);
        return super.baseReturn(request, response, root, "config");
    }

    @RequestMapping("detail")
    public String detail(@RequestParam(required = false) String key, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<String, Object>();
        if (null != key) {
            Config config = configService.findByKey(key);
            root.put("config", config);
            model.addAttribute(config);
        }
        return super.baseReturn(request, response, root, "config_detail");
    }

    @RequestMapping("update")
    public void update(Config config, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        configService.update(config);
        model.addAttribute(Constants.SUCCESS);
        view(request, response, model);
    }

    @RequestMapping("save")
    public void save(Config config, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
        configService.save(config);
        model.addAttribute(Constants.SUCCESS);
        view(request, response, model);
    }

}
