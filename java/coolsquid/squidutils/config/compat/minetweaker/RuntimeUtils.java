/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.squidutils.Runtime")
public class RuntimeUtils {

	@ZenMethod
	public static long totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	@ZenMethod
	public static long freeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	@ZenMethod
	public static long maxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	@ZenMethod
	public static int availableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}
}