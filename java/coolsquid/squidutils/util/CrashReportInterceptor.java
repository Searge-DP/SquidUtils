/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import coolsquid.squidapi.util.Utils;
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
}