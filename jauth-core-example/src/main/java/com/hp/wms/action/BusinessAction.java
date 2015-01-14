/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.wms.constant.Constant;
import com.hp.wms.service.BusinessService;


/**
 * @author huangyiq
 *
 */
public class BusinessAction extends BaseAction {

	private static final long serialVersionUID = 1867273870381291160L;
	private BusinessService businessService;
	
	@Autowired
	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}
	
	public String in() {
		businessService.in(super.getSession().get(Constant.LOGIN_USER).toString());
    	return Constant.DONE_PAGE;
    }

    public String out() {
    	businessService.out(super.getSession().get(Constant.LOGIN_USER).toString());
    	return Constant.DONE_PAGE;
    }
    
}
