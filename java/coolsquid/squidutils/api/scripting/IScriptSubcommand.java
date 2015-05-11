/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.scripting;

import java.util.Map;

public interface IScriptSubcommand {
	public abstract void run(Map<String, String> args);
}