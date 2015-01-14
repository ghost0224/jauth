package com.hp.security.jauth.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.model.AuthLog;

public class JAuthLog {

	private static BufferedWriter jauthLogWriter;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static String writeDate;
	
	public static synchronized void log(AuthLog authLog) {
		try {
			String currentDate = sdf.format(new Date());
			File log = new File(SystemInit.logPath + "jauth-" + currentDate + ".log");
			if(log.exists()) {
				if(!currentDate.equals(writeDate)) {
					if(null != jauthLogWriter) {
						jauthLogWriter.close();
						jauthLogWriter = null;
					}
					jauthLogWriter = new BufferedWriter(new FileWriter(log, true));
					writeDate = currentDate;
				}
			} else {
				log.createNewFile();
				if(null != jauthLogWriter) {
					jauthLogWriter.close();
					jauthLogWriter = null;
				}
				jauthLogWriter = new BufferedWriter(new FileWriter(log, true));
				writeDate = currentDate;
			}
			jauthLogWriter.write(authLog.toString()+"\r\n");
			jauthLogWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}