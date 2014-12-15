package com.github.coolsquid.SquidUtils.Handlers;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Reference;
import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public final class LogHandler {
	
	private static final String name = Reference.modid;
	
	public static void info(String message) {
		FMLLog.log(name, Level.INFO, message);
		}

	public static void debug(String message) {
		if (ConfigHandler.getDebugLogging())
			FMLLog.log(name, Level.DEBUG, message);
		}

	public static void warn(String message) {
		FMLLog.log(name, Level.WARN, message);
		}

	public static void error(String message) {
		FMLLog.log(name, Level.ERROR, message);
		}

	public static void fatal(String message) {
		FMLLog.log(name, Level.FATAL, message);
		}
	
	public static void fatalWarning(String message) {
		FMLLog.log(name, Level.FATAL, "-------------------------------------------------------------------------------------------");
		FMLLog.log(name, Level.FATAL, message);
		FMLLog.log(name, Level.FATAL, "-------------------------------------------------------------------------------------------");
		}
	public static void bigWarning(String message) {
		FMLLog.log(name, Level.WARN, "-------------------------------------------------------------------------------------------");
		FMLLog.log(name, Level.WARN, message);
		FMLLog.log(name, Level.WARN, "-------------------------------------------------------------------------------------------");
		}
}
