package com.hp.security.jauth.soap.hook;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.hook.SoapHook;
import com.hp.security.jauth.soap.util.SoapUtil;

@Service("soapHook")
public class SoapHookImpl implements SoapHook {

	@Override
	public String getServiceName(HttpServletRequest request) {
		return request.getRequestURI();
	}

	@Override
	public String getOperationName(HttpServletRequest request) {
		Element operation = null;
		try {
			operation = SoapUtil.getOperation(request.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SoapUtil.getOperationName(operation);
	}

}
