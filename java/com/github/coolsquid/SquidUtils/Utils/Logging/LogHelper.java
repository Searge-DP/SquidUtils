package com.github.coolsquid.SquidUtils.Utils.Logging;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;

import cpw.mods.fml.common.FMLLog;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public final class LogHelper {
	
	private static final boolean debug = ConfigHandler.DebugLogging;
	private static final String name = Data.modid;
	
	public static void writeToLog(String message) {
		
	}
	
	public static void info(String message) {
		FMLLog.log(name, Level.INFO, message);
		}
	
	public static void info(int i) {
		FMLLog.log(name, Level.INFO, i + "");
		}

	public static void debug(String message) {
		if (debug) {
			FMLLog.log(name, Level.INFO, message);
		}
	}

	public static void warn(String message) {
		FMLLog.log(name, Level.WARN, message);
		}

	public static void error(String message) {
		FMLLog.log(name, Level.ERROR, message);
		}
	
	public static void error(Throwable t) {
		FMLLog.log(name, Level.ERROR, t + "");
		}

	public static void fatal(String message) {
		FMLLog.log(name, Level.FATAL, message);
		}
	
	public static void bigWarning(Level level, String message) {
		FMLLog.log(name, level, "-------------------------------------------------------------------------------------------");
		FMLLog.log(name, level, message);
		FMLLog.log(name, level, "-------------------------------------------------------------------------------------------");
		}
}