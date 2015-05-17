/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import java.util.Map;

import coolsquid.squidutils.api.scripting.IEventAction;
import coolsquid.squidutils.api.scripting.IEventCondition;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.api.scripting.ScriptCommand;

/**
 * Will undergo big changes. You have been warned.
 * @author CoolSquid
 */
public interface ScriptingAPI {

	/**
	 * Gets the commands.
	 * @return the commands
	 */
	public abstract Map<String, ScriptCommand> getCommands();

	/**
	 * Gets the actions.
	 * @return the actions
	 */
	public abstract Map<String, IEventAction> getActions();

	/**
	 * Gets the triggers.
	 * @return the triggers
	 */
	public abstract Map<String, IEventTrigger> getTriggers();

	/**
	 * Gets the conditions.
	 * @return the conditions
	 */
	public abstract Map<String, IEventCondition> getConditions();

	/**
	 * Adds a command.
	 * @param name the name
	 * @param command the command
	 */
	public abstract void addCommand(String name, ScriptCommand command);

	/**
	 * Adds an action.
	 * @param name the name
	 * @param action the action
	 */
	public abstract void addAction(String name, IEventAction action);

	/**
	 * Adds a trigger.
	 * @param name the name
	 * @param trigger the trigger
	 */
	public abstract void addTrigger(String name, IEventTrigger trigger);

	/**
	 * Adds a condition.
	 * @param name the name
	 * @param argument the argument
	 */
	public abstract void addCondition(String name, IEventCondition argument);
}