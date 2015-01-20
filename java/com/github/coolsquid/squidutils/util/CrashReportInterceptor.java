package com.github.coolsquid.squidutils.util;

import cpw.mods.fml.common.ICrashCallable;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * Adds a message to crash reports.
 * @since 1.1.4
 * @see PackIntegrityChecker
 */

public class CrashReportInterceptor implements ICrashCallable {
	
	@Override
	public String call() throws Exception {
		return (PackIntegrityChecker.areModsMissing() || PackIntegrityChecker.areModsAdded()) + ". Are mods missing: " + PackIntegrityChecker.areModsMissing() + ". Are mods added: " + PackIntegrityChecker.areModsAdded() + ".";
	}

	@Override
	public String getLabel() {
		return "Modified";
	}
}