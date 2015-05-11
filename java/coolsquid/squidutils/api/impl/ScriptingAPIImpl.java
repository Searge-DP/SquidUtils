/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.impl;

import java.util.HashMap;

import com.google.common.collect.Maps;

import coolsquid.squidutils.api.ScriptingAPI;
import coolsquid.squidutils.api.scripting.IEventAction;
import coolsquid.squidutils.api.scripting.IEventCondition;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.api.scripting.ScriptCommand;

public class ScriptingAPIImpl implements ScriptingAPI {

	private final HashMap<String, ScriptCommand> commands = Maps.newHashMap();
	private final HashMap<String, IEventAction> actions = Maps.newHashMap();
	private final HashMap<String, IEventTrigger> triggers = Maps.newHashMap();
	private final HashMap<String, IEventCondition> conditions = Maps.newHashMap();

	@Override
	public HashMap<String, ScriptCommand> getCommands() {
		return Maps.newHashMap(this.commands);
	}

	@Override
	public HashMap<String, IEventAction> getActions() {
		return Maps.newHashMap(this.actions);
	}

	@Override
	public HashMap<String, IEventTrigger> getTriggers() {
		return Maps.newHashMap(this.triggers);
	}

	@Override
	public HashMap<String, IEventCondition> getConditions() {
		return Maps.newHashMap(this.conditions);
	}

	@Override
	public void addCommand(String name, ScriptCommand command) {
		this.commands.put(name, command);
	}

	@Override
	public void addAction(String name, IEventAction action) {
		this.actions.put(name, action);
	}

	@Override
	public void addTrigger(String name, IEventTrigger trigger) {
		this.triggers.put(name, trigger);
	}

	@Override
	public void addCondition(String name, IEventCondition argument) {
		this.conditions.put(name, argument);
	}
}