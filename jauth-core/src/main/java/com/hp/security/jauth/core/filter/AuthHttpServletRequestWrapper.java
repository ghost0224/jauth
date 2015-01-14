package com.hp.security.jauth.core.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public class AuthHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private final byte[] requestBody;
	private Logger log = Logger.getLogger(this.getClass().getName());
	private boolean hasRequestBody = false;

	public AuthHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		// init parameterMap
		request.getParameterMap();
		// init http msg
		requestBody = getRequestBody(request.getReader());
		if(requestBody.length > 0) {
			hasRequestBody = true;
		}
	}
	
	private byte[] getRequestBody(BufferedReader reader) throws IOException {
		String temp = null;
		StringBuffer body = new StringBuffer();
		while((temp = reader.readLine()) != null) {
			body.append(temp);
			log.debug("HTTP MSG: " + temp);
		}
		return body.toString().getBytes("UTF-8");
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if(requestBody.length > 0) {
			final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
			return new ServletInputStream() {
				@Override
				public int read() throws IOException {
					return bais.read();
				}
			};
		} else {
			return null;
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	public boolean hasRequestBody() {
		return hasRequestBody;
	}

}
