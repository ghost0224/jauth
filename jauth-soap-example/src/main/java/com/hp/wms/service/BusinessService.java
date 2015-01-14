package com.hp.wms.service;

import javax.jws.WebService;

@WebService
public interface BusinessService {
	
	String in(String user);
	
	String out(String user);

}
