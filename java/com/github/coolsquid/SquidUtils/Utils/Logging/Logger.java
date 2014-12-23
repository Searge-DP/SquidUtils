package com.github.coolsquid.SquidUtils.Utils.Logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	private static PrintWriter w;
	
	public static final void log(String caller, String level, String message) {
		if (level.matches("(INFO|WARN|ERROR|FATAL)")) {
			loglist.add("[" + caller + "]" + "[" + level + "]: " + message);
		}
		else {
			throw new LoggingException("Level is wrong");
		}
	}
	
	public static void save() {
		int a = 0;
		int b = 0;
		File log = new File("/logs/EventLogs/client-"+ b + ".log");
		log.mkdirs();
		b++;
		try {
			log.createNewFile();
			w = new PrintWriter(log);
			b++;
			while (a < loglist.size()) {
				if (a == 0) {
					w.format("#Log");
				}
				w.format("\n" + loglist.get(a));
				a++;
			}
			loglist.clear();
			w.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new LoggingException("File not found");
		}
	}
}