package com.github.coolsquid.SquidUtils.Utils;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

public final class ShutdownHandler {
	
	public static void run() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (Data.wV()) {
					LogHelper.info("You are not running Minecraft 1.7.10. No support will be given for any errors.");
				}
			}
		});
	}
}