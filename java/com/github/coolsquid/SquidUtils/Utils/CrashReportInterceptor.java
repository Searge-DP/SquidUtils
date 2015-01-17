package com.github.coolsquid.SquidUtils.Utils;

import cpw.mods.fml.common.ICrashCallable;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * Adds a message to crash reports.
 *
 */

public class CrashReportInterceptor implements ICrashCallable {
	
	@Override
	public String call() throws Exception {
		return "Are mods missing: " + PackIntegrityChecker.areModsMissing() + ". Are mods added: " + PackIntegrityChecker.areModsAdded() + ".";
	}
 
	@Override
	public String getLabel() {
		return "Modified";
	}
}