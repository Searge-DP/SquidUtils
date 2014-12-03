package com.github.coolsquid.SquidUtils.Handlers;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public final class LogHandler {

	public static void info(String message) {
		FMLLog.log("SquidUtils", Level.INFO, message);
		}

	public static void debug(String message) {
		FMLLog.log("SquidUtils", Level.DEBUG, message);
		}

	public static void warn(String message) {
		FMLLog.log("SquidUtils", Level.WARN, message);
		}

	public static void error(String message) {
		FMLLog.log("SquidUtils", Level.ERROR, message);
		}

	public static void fatal(String message) {
		FMLLog.log("SquidUtils", Level.FATAL, message);
		}
	
	public static void bigWarning(String message) {
		FMLLog.log("SquidUtils", Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log("SquidUtils", Level.FATAL, "!!!!!!!!!!!!!!!" + message + "!!!!!!!!!!!!!!!");
		FMLLog.log("SquidUtils", Level.FATAL, "-------------------------------------------------------------------------------------------");
		}
}
