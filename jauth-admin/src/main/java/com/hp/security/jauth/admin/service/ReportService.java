package com.hp.security.jauth.admin.service;

import java.util.List;

import com.hp.security.jauth.admin.model.report.PieWithController;
import com.hp.security.jauth.admin.model.report.TrendLineWithAccess;

public interface ReportService {

	List<PieWithController> pieWithModule(boolean business);

	List<TrendLineWithAccess> trendLineWithAccess(boolean business);
	
}
