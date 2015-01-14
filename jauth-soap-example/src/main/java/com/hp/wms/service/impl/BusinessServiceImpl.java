package com.hp.wms.service.impl;

import javax.jws.WebMethod;

import com.hp.wms.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

	@WebMethod
	public String in(String user) {
		return user + " in!";
	}

	@WebMethod
	public String out(String user) {
		return user + " out!";
	}

}
