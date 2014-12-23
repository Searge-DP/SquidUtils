package com.github.coolsquid.SquidUtils.Utils.Logging;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.coolsquid.SquidUtils.Utils.Exception.LoggingException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Logger {
	
	private static List<String> loglist = new ArrayList<String>();
	
	public static final void add(String caller, String level, String message) {
		SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
		String time = t.format(Calendar.getInstance().getTime());
		if (level.matches("(INFO|WARN|ERROR|FATAL)")) {
			loglist.add("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
		}
		else {
			throw new LoggingException("Level is wrong");
		}
	}
	
	public static final void save() {
		int a = 0;
		
		SimpleDateFormat ft = new SimpleDateFormat("HH-mm-ss");
		SimpleDateFormat fd = new SimpleDateFormat("dd-MM-YYYY");
		String fileTime = ft.format(Calendar.getInstance().getTime());
		String fileDate = fd.format(Calendar.getInstance().getTime());
		
		File logFolder = new File(System.getProperty("user.dir") + "/logs/EventLogs");
		File log = new File("./logs/EventLogs/", "client-" + fileTime + "-" + fileDate + ".log");
		PrintWriter w;
		try {
			logFolder.mkdirs();
			if (!loglist.isEmpty()) {
				w = new PrintWriter(log);
				while (a < loglist.size()) {
					if (a == 0) {
						w.format("#Log");
					}
					w.format("\n" + loglist.get(a));
					a++;
				}
				w.close();
				loglist.clear();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}