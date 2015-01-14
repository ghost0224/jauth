package com.hp.security.jauth.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	public static String getBusinessUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		for(int i=0; i<3; i++) {
			url = url.substring(url.indexOf("/")+1);
		}
		return url;
	}
	
	public static String getBusinessUrl(String url) {
		for(int i=0; i<3; i++) {
			url = url.substring(url.indexOf("/")+1);
		}
		int temp = url.indexOf("?");
		if(temp > 0) {
			url = url.substring(0, temp);
		}
		return url;
	}
	
	public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }
	
	public static String getRemoteIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null) {
        	return ip;
        }
        String[] ips = ip.split(",");
        if (ips.length > 1) {
            return ips[0];
        } else {
            return ip;
        }
    }
	
}
