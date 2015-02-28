/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;

import com.github.coolsquid.squidutils.util.script.EventInfo;
import com.google.common.collect.ImmutableMap;

public class ScriptingAPI {
	
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
		public void run(EntityLivingBase entity, EventInfo info);
		
		/**
		 * Inits the.
		 *
		 * @param info the info
		 */
		public void init(EventInfo info);
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
		public ArrayList<EventInfo> info();
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
		public void run(String condition);
	}
}