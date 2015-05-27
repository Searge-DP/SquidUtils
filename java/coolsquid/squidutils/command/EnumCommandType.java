/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.util.interfaces.Factory;

public enum EnumCommandType {

	INFO(new Factory<CommandInfo>() {@Override public CommandInfo newInstance(String... params) {return new CommandInfo(params[0], params[1], params[2]);}}),
	OPENURL(new Factory<CommandOpenUrl>() {@Override public CommandOpenUrl newInstance(String... params) {return new CommandOpenUrl(params[0], params[1], params[2]);}}),
	WEBINFO(new Factory<CommandWeb>() {@Override public CommandWeb newInstance(String... params) {return new CommandWeb(params[0], params[1], params[2]);}}),
	BASE(new Factory<CommandBase>() {@Override public CommandBase newInstance(String... params) {return new CommandBase(params[0], params[1]);}});

	private Factory<? extends CommandBase> factory;

	private EnumCommandType(Factory<? extends CommandBase> factory) {
		this.factory = factory;
	}

	public Factory<? extends CommandBase> getFactory() {
		return this.factory;
	}

	public CommandBase newInstance(String... parameters) {
		return this.factory.newInstance(parameters);
	}
}