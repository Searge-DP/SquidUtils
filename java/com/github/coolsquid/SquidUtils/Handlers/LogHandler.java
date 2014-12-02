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

	public static void info(String M, String message) {
		FMLLog.log(M, Level.INFO, message);
		}

	public static void debug(String M, String message) {
		FMLLog.log(M, Level.DEBUG, message);
		}

	public static void warn(String M, String message) {
		FMLLog.log(M, Level.WARN, message);
		}

	public static void error(String M, String message) {
		FMLLog.log(M, Level.ERROR, message);
		}

	public static void fatal(String M, String message) {
		FMLLog.log(M, Level.FATAL, message);
		}
	
	public static void bigWarning(String M, String message) {
		FMLLog.log(M, Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log(M, Level.FATAL, "!!!!!!!!!!!!!!!" + message + "!!!!!!!!!!!!!!!");
		FMLLog.log(M, Level.FATAL, "-------------------------------------------------------------------------------------------");
		}
}
