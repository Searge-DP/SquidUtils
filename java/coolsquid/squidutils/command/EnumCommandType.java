/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.util.interfaces.IFactory;

public enum EnumCommandType {

	INFO(new IFactory<CommandInfo>() {@Override public CommandInfo newInstance(String... params) {return new CommandInfo(params[0], params[1], params[2]);}}),
	OPENURL(new IFactory<CommandOpenUrl>() {@Override public CommandOpenUrl newInstance(String... params) {return new CommandOpenUrl(params[0], params[1], params[2]);}}),
	WEBINFO(new IFactory<CommandWeb>() {@Override public CommandWeb newInstance(String... params) {return new CommandWeb(params[0], params[1], params[2]);}}),
	BASE(new IFactory<CommandBase>() {@Override public CommandBase newInstance(String... params) {return new CommandBase(params[0], params[1]);}});

	private IFactory<? extends CommandBase> factory;

	private EnumCommandType(IFactory<? extends CommandBase> factory) {
		this.factory = factory;
	}

	public IFactory<? extends CommandBase> getFactory() {
		return this.factory;
	}

	public CommandBase newInstance(String... parameters) {
		return this.factory.newInstance(parameters);
	}
}