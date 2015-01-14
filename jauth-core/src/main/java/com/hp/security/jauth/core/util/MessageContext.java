package com.hp.security.jauth.core.util;

import java.util.HashMap;
import java.util.Map;

public class MessageContext {

	public static Map<String, String> messages = new HashMap<String, String>();
	
	public static String getMessage(String code) {
		return MessageContext.messages.get(code);
	}
	
	public static String getMessage(String code, String[]... params) {
		String value = MessageContext.messages.get(code);
		for(String[] param : params) {
			value = value.replaceAll("\\{" + param[0].toString() + "\\}", param[1].toString());
		}
		return value;
	}
	
}
