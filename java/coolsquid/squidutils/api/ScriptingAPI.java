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

import coolsquid.squidutils.util.script.EventInfo;

/**
 * Planning to remove the whole scripting system.
 */
public class ScriptingAPI {
	
	/** The commands */
	@Deprecated
	private static final HashMap<String, ScriptCommand> commands = new HashMap<String, ScriptCommand>();
	
	/** The actions. */
	@Deprecated
	private static final HashMap<String, IEventAction> actions = new HashMap<String, IEventAction>();
	
	/** The triggers. */
	@Deprecated
	private static final HashMap<String, IEventTrigger> triggers = new HashMap<String, IEventTrigger>();
	
	/** The conditions. */
	@Deprecated
	private static final HashMap<String, IEventCondition> conditions = new HashMap<String, IEventCondition>();
	
	/**
	 * Gets the commands.
	 * Cannot be modified.
	 *
	 * @return the commands
	 */
	public static ImmutableMap<String, ScriptCommand> getCommands() {
		return ImmutableMap.copyOf(commands);
	}
	
	/**
	 * Gets the actions.
	 * Cannot be modified.
	 * 
	 * @return the actions
	 */
	public static ImmutableMap<String, IEventAction> getActions() {
		return ImmutableMap.copyOf(actions);
	}
	
	/**
	 * Gets the triggers.
	 * Cannot be modified.
	 * 
	 * @return the triggers
	 */
	public static ImmutableMap<String, IEventTrigger> getTriggers() {
		return ImmutableMap.copyOf(triggers);
	}
	
	/**
	 * Gets the conditions.
	 * Cannot be modified.
	 *
	 * @return the conditions
	 */
	public static ImmutableMap<String, IEventCondition> getConditions() {
		return ImmutableMap.copyOf(conditions);
	}
	
	public static void addCommand(String name, ScriptCommand command) {
		commands.put(name, command);
	}
	
	/**
	 * Adds an action.
	 *
	 * @param name the name
	 * @param action the action
	 */
	public static void addAction(String name, IEventAction action) {
		actions.put(name, action);
	}
	
	/**
	 * Adds a trigger.
	 *
	 * @param name the name
	 * @param trigger the trigger
	 */
	public static void addTrigger(String name, IEventTrigger trigger) {
		triggers.put(name, trigger);
	}
	
	/**
	 * Adds a condition.
	 *
	 * @param name the name
	 * @param argument the argument
	 */
	public static void addCondition(String name, IEventCondition argument) {
		conditions.put(name, argument);
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

	public interface IScriptSubcommand {
		void run(Map<String, String> args);
	}
	
	/**
	 * The Interface IEventAction.
	 */
	public interface IEventAction {
		
		/**
		 * Run.
		 *
		 * @param entity the entity
		 * @param info the info
		 */
		void run(EntityLivingBase entity, EventInfo info);
		
		/**
		 * Inits the.
		 *
		 * @param info the info
		 */
		void init(EventInfo info);
	}
	
	/**
	 * The Interface IEventTrigger.
	 */
	public interface IEventTrigger {
		
		/**
		 * Info.
		 *
		 * @return the array list
		 */
		List<EventInfo> info();
	}
	
	/**
	 * The Interface IEventCondition.
	 */
	public interface IEventCondition {
		
		/**
		 * Run.
		 *
		 * @param condition the condition
		 */
		void run(String condition);
	}
}