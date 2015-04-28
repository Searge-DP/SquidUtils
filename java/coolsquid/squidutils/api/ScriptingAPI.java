/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import coolsquid.squidutils.util.script.EventInfo;

public class ScriptingAPI {

	private final HashMap<String, ScriptCommand> commands = Maps.newHashMap();
	private final HashMap<String, IEventAction> actions = Maps.newHashMap();
	private final HashMap<String, IEventTrigger> triggers = Maps.newHashMap();
	private final HashMap<String, IEventCondition> conditions = Maps.newHashMap();

	public ImmutableMap<String, ScriptCommand> getCommands() {
		return ImmutableMap.copyOf(this.commands);
	}

	public ImmutableMap<String, IEventAction> getActions() {
		return ImmutableMap.copyOf(this.actions);
	}

	public ImmutableMap<String, IEventTrigger> getTriggers() {
		return ImmutableMap.copyOf(this.triggers);
	}

	public ImmutableMap<String, IEventCondition> getConditions() {
		return ImmutableMap.copyOf(this.conditions);
	}

	public void addCommand(String name, ScriptCommand command) {
		this.commands.put(name, command);
	}

	public void addAction(String name, IEventAction action) {
		this.actions.put(name, action);
	}

	public void addTrigger(String name, IEventTrigger trigger) {
		this.triggers.put(name, trigger);
	}

	public void addCondition(String name, IEventCondition argument) {
		this.conditions.put(name, argument);
	}

	public static class ScriptCommand {
		private final Map<String, IScriptSubcommand> subcommands;

		public ScriptCommand(Map<String, IScriptSubcommand> subcommands) {
			this.subcommands = subcommands;
		}

		public Map<String, IScriptSubcommand> getSubcommands() {
			return this.subcommands;
		}
	}

	public static interface IScriptSubcommand {
		void run(Map<String, String> args);
	}

	public static interface IEventAction {
		void run(EntityLivingBase entity, EventInfo info);
		void init(EventInfo info);
	}

	public static interface IEventTrigger {
		List<EventInfo> info();
	}

	public static interface IEventCondition {
		void run(String condition);
	}

	public static ScriptingAPI create() {
		return new ScriptingAPI();
	}
}