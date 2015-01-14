package com.hp.full.webservice.impl;

import javax.jws.WebMethod;

import com.hp.full.webservice.BusinessService;

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
