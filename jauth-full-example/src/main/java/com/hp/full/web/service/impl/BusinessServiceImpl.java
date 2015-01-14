package com.hp.full.web.service.impl;

import org.springframework.stereotype.Service;

import com.hp.full.web.service.BusinessService;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Override
	public void in(String username) {
		System.out.println("标准入库");
		if(username.equals("huangyiq")) {
			System.out.println("皇家排序");
		} else if(username.equals("zhuangren")) {
			System.out.println("庄家排序");
		} else if(username.equals("duanwenbing")) {
			System.out.println("段家排序");
		} else {
			System.out.println("普通排序");
		}
		System.out.println("标准找库位逻辑");
		if(username.equals("huangyiq")) {
			System.out.println("对库位结果集合排序");
		}
		System.out.println("标准扫描入库");
		if(username.equals("zhuangren")) {
			System.out.println("入库记录单独存放文件备份");
		}
		System.out.println("结束入库");
	}

	@Override
	public void out(String username) {
		System.out.println("标准出库");
		if(username.equals("huangyiq")) {
			System.out.println("皇家拣货");
		} else if(username.equals("zhuangren")) {
			System.out.println("庄家分拣");
		} else if(username.equals("duanwenbing")) {
			System.out.println("段家拆单");
		} else {
			System.out.println("标准拣货");
		}
		System.out.println("出库结束");
	}

}
