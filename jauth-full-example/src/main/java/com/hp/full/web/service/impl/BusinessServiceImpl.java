package com.hp.full.web.service.impl;

import org.springframework.stereotype.Service;

import com.hp.full.web.service.BusinessService;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Override
	public void in(String username) {
		System.out.println("��׼���");
		if(username.equals("huangyiq")) {
			System.out.println("�ʼ�����");
		} else if(username.equals("zhuangren")) {
			System.out.println("ׯ������");
		} else if(username.equals("duanwenbing")) {
			System.out.println("�μ�����");
		} else {
			System.out.println("��ͨ����");
		}
		System.out.println("��׼�ҿ�λ�߼�");
		if(username.equals("huangyiq")) {
			System.out.println("�Կ�λ�����������");
		}
		System.out.println("��׼ɨ�����");
		if(username.equals("zhuangren")) {
			System.out.println("����¼��������ļ�����");
		}
		System.out.println("�������");
	}

	@Override
	public void out(String username) {
		System.out.println("��׼����");
		if(username.equals("huangyiq")) {
			System.out.println("�ʼҼ��");
		} else if(username.equals("zhuangren")) {
			System.out.println("ׯ�ҷּ�");
		} else if(username.equals("duanwenbing")) {
			System.out.println("�μҲ�");
		} else {
			System.out.println("��׼���");
		}
		System.out.println("�������");
	}

}
