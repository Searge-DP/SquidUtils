package com.github.coolsquid.Testy.Utils.Logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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

public class Logger extends Thread {
	
	private static List<String> loglist = new ArrayList<String>();
	
	private static SimpleDateFormat t = new SimpleDateFormat("HH:mm:ss");
	public static final void log(String caller, Level level, String message, boolean print) {
		if (message.length() > 150) {
			throw new LoggingException("The message was too long!");
		}
		
		String time = t.format(Calendar.getInstance().getTime());
		if (level.equals(Level.INFO)) {
			loglist.add("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			if (print) {
				System.out.println("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			}
		}
		else if (level.equals(Level.WARN) || level.equals(Level.ERROR) || level.equals(Level.FATAL)) {
			loglist.add("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			if (print) {
				System.err.println("[" + time + "]" + "[" + caller + "]" + "[" + level + "]: " + message);
			}
		}
		else {
			throw new LoggingException("Level is wrong");
		}
	}
	
	private static SimpleDateFormat ft = new SimpleDateFormat("HH-mm-ss");
	private static SimpleDateFormat fd = new SimpleDateFormat("dd-MM-YYYY");
	public static final void save(String location, String name) {
		int a = 0;
		
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
				w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(log)));
				while (a < loglist.size()) {
					if (a == 0) {
						w.print("#Log");
					}
					w.print("\n");w.print(loglist.get(a));
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