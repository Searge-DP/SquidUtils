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
	
	public static String A = "SquidUtils";

	public static void info(String message) {
		FMLLog.log(A, Level.INFO, message);
		}

	public static void debug(String message) {
		FMLLog.log(A, Level.DEBUG, message);
		}

	public static void warn(String message) {
		FMLLog.log(A, Level.WARN, message);
		}

	public static void error(String message) {
		FMLLog.log(A, Level.ERROR, message);
		}

	public static void fatal(String message) {
		FMLLog.log(A, Level.FATAL, message);
		}
	
	public static void bigWarning(String message) {
		FMLLog.log(A, Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log(A, Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log(A, Level.FATAL, message);
		FMLLog.log(A, Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log(A, Level.FATAL, "-------------------------------------------------------------------------------------------");
		}
}
