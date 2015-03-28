/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import com.github.coolsquid.squidapi.util.Utils;

import cpw.mods.fml.common.ICrashCallable;

public class CrashReportInterceptor {

	public static class Modified implements ICrashCallable {
		
		@Override
		public String call() throws Exception {
			return Utils.newString((PackIntegrityChecker.INSTANCE.haveModsBeenRemoved() || PackIntegrityChecker.INSTANCE.haveModsBeenAdded()), ". Have mods been removed: ", PackIntegrityChecker.INSTANCE.haveModsBeenRemoved(), ". Have mods been added: ", PackIntegrityChecker.INSTANCE.haveModsBeenAdded(), ".");
		}

		@Override
		public String getLabel() {
			return "Modified";
		}
	}

	public static class CrashMessage implements ICrashCallable {
	
		private final String label;
		private final String message;

		public CrashMessage(String label, String message) {
			this.label = label;
			this.message = message;
		}
		
		@Override
		public String call() throws Exception {
			return this.message;
		}

		@Override
		public String getLabel() {
			return this.label;
		}
	}
}