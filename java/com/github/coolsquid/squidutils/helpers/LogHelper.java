/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.helpers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.util.ModInfo;

public class LogHelper {
	
public static final Logger logger = LogManager.getLogger(ModInfo.modid);
	
	private static void log(Level level, String msg) {
		logger.log(level, msg);
		SquidAPI.logger.log("SquidAPI", com.github.coolsquid.squidapi.logging.Level.getLevel(level.toString()), msg, false);
	}
	
	public static void info(Object... msg) {
		log(Level.INFO, Utils.newString(msg));
	}
	
	public static void warn(Object... msg) {
		log(Level.WARN, Utils.newString(msg));
	}
	
	public static void error(Object... msg) {
		log(Level.ERROR, Utils.newString(msg));
	}
	
	public static void fatal(Object... msg) {
		log(Level.FATAL, Utils.newString(msg));
	}
	
	public static void bigWarning(Level level, Object... msg) {
		log(level, "-------------------------------------------------------------------------------------");
		log(level, Utils.newString(msg));
		log(level, "-------------------------------------------------------------------------------------");
	}
}