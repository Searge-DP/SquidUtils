/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import coolsquid.squidapi.command.CommandBase;

public enum EnumCommandType {

	INFO {
		@Override
		public CommandBase newInstance(String... parameters) {
			return new CommandInfo(parameters[0], parameters[1], parameters[2]);
		}
	},
	OPENURL {
		@Override
		public CommandBase newInstance(String... parameters) {
			return new CommandOpenUrl(parameters[0], parameters[1], parameters[2]);
		}
	},
	WEBINFO {
		@Override
		public CommandBase newInstance(String... parameters) {
			return new CommandWeb(parameters[0], parameters[1], parameters[2]);
		}
	},
	BASE {
		@Override
		public CommandBase newInstance(String... parameters) {
			return new CommandBase(parameters[0], parameters[1]);
		}
	};

	public abstract CommandBase newInstance(String... parameters);
}