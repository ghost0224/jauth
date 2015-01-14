package com.hp.full.webservice;

import javax.jws.WebService;

@WebService
public interface BusinessService {
	
	String in(String user);
	
	String out(String user);

}
