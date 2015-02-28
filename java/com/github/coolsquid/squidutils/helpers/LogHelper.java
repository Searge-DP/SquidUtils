/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.helpers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidutils.util.ModInfo;

public class LogHelper {
	
	public static final Logger logger = LogManager.getLogger(ModInfo.modid);
	
	private static void log(Level level, String msg) {
		logger.log(level, msg);
		SquidAPI.logger.log("SquidUtils", com.github.coolsquid.squidapi.logging.Level.getLevel(level.toString()), msg, false);
	}
	
	public static void info(String msg) {
		log(Level.INFO, msg);
		}
	
	public static void info(int i) {
		log(Level.INFO, i + "");
		}
	
	public static void warn(String msg) {
		log(Level.WARN, msg);
		}
	
	public static void error(String msg) {
		log(Level.ERROR, msg);
		}
	
	public static void error(Throwable t) {
		log(Level.ERROR, t + "");
		}
	
	public static void fatal(String msg) {
		log(Level.FATAL, msg);
		}
	
	public static void bigWarning(Level level, String msg) {
		log(level, "-------------------------------------------------------------------------------------");
		log(level, msg);
		log(level, "-------------------------------------------------------------------------------------");
		}
}