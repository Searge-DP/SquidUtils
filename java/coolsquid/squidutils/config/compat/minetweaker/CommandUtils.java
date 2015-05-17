/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.minetweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.google.common.base.Joiner;

import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidutils.command.EnumCommandType;

@ZenClass("mods.squidutils.Commands")
public class CommandUtils {

	@ZenMethod
	public static void createCommand(String type, String[] parameters) {
		ServerHelper.registerCommand(EnumCommandType.valueOf(type).newInstance(parameters));
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

	@ZenMethod
	public static String[] getCommands() {
		return ServerHelper.getCommands().keySet().toArray(new String[] {});
	}

	@ZenMethod
	public static String getCommandsString() {
		return Joiner.on(", ").join(ServerHelper.getCommands().keySet());
	}
}