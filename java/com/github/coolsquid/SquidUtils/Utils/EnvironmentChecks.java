package com.github.coolsquid.SquidUtils.Utils;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

public class EnvironmentChecks {
	
	public static final void preInit() {
		
		if (Data.wV()) {
			LogHelper.bigWarning(Level.WARN, "MC is not running 1.7.10! Problems may occur. Do not report any errors.");
		}
		
		if (!Data.isJava64bit() && Data.isOs64bit()) {
			LogHelper.bigWarning(Level.WARN, "Your OS is 64 bit, but your not running 64 bit Java! Why would you do that?!");
		}
		
		if (Data.developmentEnvironment) {
			LogHelper.info("Running in a dev environment.");
		}
	}
}