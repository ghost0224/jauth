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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.security.jauth.admin.model.report.PieWithController;
import com.hp.security.jauth.admin.model.report.TrendLineWithAccess;
import com.hp.security.jauth.admin.service.ReportService;

import freemarker.template.TemplateException;


/**
 * @author huangyiq
 * yiqingh@hp.com
 */
@Controller
@RequestMapping("report")
public class ReportController extends BaseController {
	
	private ReportService reportService;
	
	@Autowired
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@RequestMapping("pieWithModule")
    public String pieWithController(@RequestParam(required=false)boolean business, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
		List<PieWithController> data = reportService.pieWithModule(business);
		return doProcess(request, response, business, data, "pieWithModule", model);
    }

	@RequestMapping("trendLineWithAccess")
    public String trendLineWithAccess(@RequestParam(required=false)boolean business, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException,
            TemplateException {
		List<TrendLineWithAccess> data = reportService.trendLineWithAccess(business);
		return doProcess(request, response, business, data, "trendLineWithAccess", model);
    }
	
	private String doProcess(HttpServletRequest request, HttpServletResponse response, boolean business, List data, String template, Model model) throws IOException, TemplateException {
		Map<String, Object> root = new HashMap<String, Object>();
        root.put("data", data);
        root.put("business", business);
        model.addAttribute(data);
        model.addAttribute(business);
        return super.baseReturn(request, response, root, template);
	}
	
}
