/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import java.lang.reflect.InvocationTargetException;

import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.util.Utils;

public enum EnumCommandType {

	INFO(CommandInfo.class),
	OPENURL(CommandOpenUrl.class),
	WEBINFO(CommandWeb.class),
	BASE(CommandBase.class);

	private Class<? extends CommandBase> clazz;

	private EnumCommandType(Class<? extends CommandBase> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends CommandBase> getCommandClass() {
		return this.clazz;
	}

	public CommandBase createCommand(Object... parameters) {
		try {
			return this.clazz.getConstructor(Utils.getClasses(parameters)).newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}