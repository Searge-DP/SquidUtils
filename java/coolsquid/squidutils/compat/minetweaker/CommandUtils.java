/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidutils.command.EnumCommandType;

@ZenClass("mods.squidutils.Commands")
public class CommandUtils {

	@ZenMethod
	public static void createCommand(String type, String[] parameters) {
		ServerHelper.registerCommand(EnumCommandType.valueOf(type).createCommand((Object[]) parameters));
	}

	@ZenMethod
	public static void removeCommand(String name) {
		ServerHelper.removeCommand(name);
	}

	@ZenMethod
	public static void removeCommands(String[] names) {
		for (String name: names) {
			ServerHelper.removeCommand(name);
		}
	}
}