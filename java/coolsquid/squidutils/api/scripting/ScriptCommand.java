/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.scripting;

import java.util.Map;

public class ScriptCommand {
	private final Map<String, IScriptSubcommand> subcommands;

	public ScriptCommand(Map<String, IScriptSubcommand> subcommands) {
		this.subcommands = subcommands;
	}

	public Map<String, IScriptSubcommand> getSubcommands() {
		return this.subcommands;
	}
}