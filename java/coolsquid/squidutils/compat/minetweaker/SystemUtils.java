/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.squidutils.System")
public class SystemUtils {

	@ZenMethod
	public static String getProperty(String property) {
		return System.getProperty(property);
	}

	@ZenMethod
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	@ZenMethod
	public static String lineSeparator() {
		return System.lineSeparator();
	}
}