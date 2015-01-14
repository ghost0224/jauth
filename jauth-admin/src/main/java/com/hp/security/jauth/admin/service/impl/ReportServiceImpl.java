package com.hp.security.jauth.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.admin.dao.ReportDao;
import com.hp.security.jauth.admin.model.report.PieWithController;
import com.hp.security.jauth.admin.model.report.TrendLineWithAccess;
import com.hp.security.jauth.admin.service.ReportService;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	private ReportDao reportDao;
	
	@Autowired
	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public List<PieWithController> pieWithModule(boolean business) {
		return reportDao.pieWithController(business);
	}

	public List<TrendLineWithAccess> trendLineWithAccess(boolean business) {
		return reportDao.trendLineWithAccess(business);
	}

}
