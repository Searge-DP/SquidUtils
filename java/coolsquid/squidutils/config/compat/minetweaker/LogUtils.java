/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import coolsquid.squidutils.SquidUtils;

@ZenClass("mods.squidutils.Logger")
public class LogUtils {

	@ZenMethod
	public static void info(String msg) {
		SquidUtils.instance().info(msg);
	}

	@ZenMethod
	public static void debug(String msg) {
		SquidUtils.instance().debug(msg);
	}

	@ZenMethod
	public static void warn(String msg) {
		SquidUtils.instance().warn(msg);
	}

	@ZenMethod
	public static void error(String msg) {
		SquidUtils.instance().error(msg);
	}

	@ZenMethod
	public static void fatal(String msg) {
		SquidUtils.instance().fatal(msg);
	}
}