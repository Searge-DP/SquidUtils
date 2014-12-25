package com.coolsquid.Testy.Utils.Logging;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Logger {
	
	private static List<String> loglist = new ArrayList<String>();
	
	public static final void log(String caller, String level, String message, boolean print) {
		SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
		String time = t.format(Calendar.getInstance().getTime());
		if (level.matches("(INFO|WARN)")) {
			loglist.add("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			if (print) {
				System.out.println("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			}
		}
		else if (level.matches("(ERROR|FATAL)")) {
			loglist.add("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			if (print) {
				System.err.println("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			}
		}
		else {
			throw new LoggingException("Level is wrong");
		}
	}
	
	public static final void save(String location, String name) {
		int a = 0;
		
		SimpleDateFormat ft = new SimpleDateFormat("HH-mm-ss");
		SimpleDateFormat fd = new SimpleDateFormat("dd-MM-YYYY");
		String fileTime = ft.format(Calendar.getInstance().getTime());
		String fileDate = fd.format(Calendar.getInstance().getTime());
		
		File logFolder = new File("./" + location);
		File log;
		if (name.isEmpty()) {
			log = new File("./" + location, fileTime + "-" + fileDate + ".log");
		}
		else {
			log = new File("./" + location, name + "-" + fileTime + "-" + fileDate + ".log");
		}
		
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