package com.hp.security.jauth.soap.wss;

import java.util.Map;
import java.util.Vector;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.handler.RequestData;

import com.hp.security.jauth.core.util.ContextHolder;

public class WSSInterceptor extends WSS4JInInterceptor {

	public WSSInterceptor() {
		super();
	}

	public WSSInterceptor(boolean ignore) {
		super(ignore);
	}

	public WSSInterceptor(Map<String, Object> properties) {
		super(properties);
	}

	@Override
	public void setIgnoreActions(boolean i) {
		super.setIgnoreActions(i);
	}

	@Override
	public Object getProperty(Object msgContext, String key) {
		return super.getProperty(msgContext, key);
	}

	@Override
	protected void computeAction(SoapMessage msg, RequestData reqData) {
		super.computeAction(msg, reqData);
	}

	@Override
	protected void doResults(SoapMessage msg, String actor, SOAPMessage doc,
			Vector wsResult) throws SOAPException, XMLStreamException {
		super.doResults(msg, actor, doc, wsResult);
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		if(ContextHolder.isPassed() == null || !ContextHolder.isPassed()) {
			super.handleMessage(msg);
		}
	}

}
