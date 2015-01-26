/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import cpw.mods.fml.common.ICrashCallable;

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