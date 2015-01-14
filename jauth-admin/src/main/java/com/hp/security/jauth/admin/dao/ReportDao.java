package com.hp.security.jauth.admin.dao;

import java.util.List;

import com.hp.security.jauth.admin.model.report.PieWithController;
import com.hp.security.jauth.admin.model.report.TrendLineWithAccess;

public interface ReportDao {
	
	List<PieWithController> pieWithController(boolean business);

	List<TrendLineWithAccess> trendLineWithAccess(boolean business);
	
}
