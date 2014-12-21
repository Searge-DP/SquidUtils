package com.github.coolsquid.SquidUtils.Utils;

import org.apache.logging.log4j.Level;

public class EnvironmentChecks {
	
	public static final void preInit() {
		
		if (Data.wV()) {
			LogHelper.bigWarning(Level.WARN, "MC is not running 1.7.10! Problems may occur. Do not report any errors.");
		}
		
		if (!Data.isJava64bit() && Data.isOs64bit()) {
			LogHelper.bigWarning(Level.WARN, "Your OS is 64 bit, but your running 32 bit Java! Why would you do that?!");
		}
		
		if (Data.developmentEnvironment) {
			LogHelper.info("Running in a dev environment.");
		}
		
		if (!Data.isJava7()) {
			LogHelper.warn("Minecraft is not running Java 7. Java 7 is optimal for this mod.");
		}
	}
}