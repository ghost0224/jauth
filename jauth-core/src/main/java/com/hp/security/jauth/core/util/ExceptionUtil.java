package com.hp.security.jauth.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.exception.AuthException;

import freemarker.template.TemplateException;

@Service
public class ExceptionUtil {
	
	private FreemarkerUtil freemarkerUtil;
	
	@Autowired
	public void setFreemarkerUtil(FreemarkerUtil freemarkerUtil) {
		this.freemarkerUtil = freemarkerUtil;
	}

	public void throwDefaultTemplate(ServletResponse response, Exception ex) {
		Map<String, Object> root = new HashMap<String, Object>();
		String code = "";
		if(null != ex && ex instanceof AuthException) {
			AuthException aex = (AuthException)ex;
			code = aex.getCode();
		}
		root.put("code", code);
		root.put("ex", ex);
		String view;
		ServletOutputStream out = null;
		try {
			view = freemarkerUtil.buildView(root, "exception");
			response.setContentType("text/html");
			out = response.getOutputStream();
			out.write(view.getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if(null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
