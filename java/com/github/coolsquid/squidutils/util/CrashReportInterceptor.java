/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import com.github.coolsquid.squidapi.util.Utils;

import cpw.mods.fml.common.ICrashCallable;

public class CrashReportInterceptor implements ICrashCallable {
	
	@Override
	public String call() throws Exception {
		return Utils.newString((PackIntegrityChecker.haveModsBeenRemoved() || PackIntegrityChecker.haveModsBeenAdded()), ". Have mods been removed: ", PackIntegrityChecker.haveModsBeenRemoved(), ". Have mods been added: ", PackIntegrityChecker.haveModsBeenAdded(), ".");
	}

	@Override
	public String getLabel() {
		return "Modified";
	}
}