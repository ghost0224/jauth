package com.hp.wms.service.impl;

import org.springframework.stereotype.Service;

import com.hp.wms.service.BusinessService;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Override
	public void in(String username) {
		System.out.println("standard in");
	}

	@Override
	public void out(String username) {
		System.out.println("standard out");
	}

}
