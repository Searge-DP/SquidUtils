/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.squidutils.Strings")
public class StringUtils {

	@ZenMethod
	public static boolean contains(String string, String otherString) {
		return string.contains(otherString);
	}

	@ZenMethod
	public static boolean equals(String string, String otherString) {
		return string.equals(otherString);
	}

	@ZenMethod
	public static boolean equalsIgnoreCase(String string, String otherString) {
		return string.equalsIgnoreCase(otherString);
	}

	@ZenMethod
	public static String[] split(String string, String regex) {
		return string.split(regex);
	}

	@ZenMethod
	public static boolean matches(String string, String regex) {
		return string.matches(regex);
	}

	@ZenMethod
	public static String substring(String string, int beginIndex, int endIndex) {
		return string.substring(beginIndex, endIndex);
	}

	@ZenMethod
	public static String substring(String string, int beginIndex) {
		return string.substring(beginIndex);
	}

	@ZenMethod
	public static String toLowerCase(String string) {
		return string.toLowerCase();
	}

	@ZenMethod
	public static String toUpperCase(String string) {
		return string.toLowerCase();
	}
}