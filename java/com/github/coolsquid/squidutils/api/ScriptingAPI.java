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
	
	@Deprecated
	private static final HashMap<String, IEventAction> actions = new HashMap<String, IEventAction>();
	@Deprecated
	private static final HashMap<String, IEventTrigger> triggers = new HashMap<String, IEventTrigger>();
	@Deprecated
	private static final HashMap<String, IEventCondition> conditions = new HashMap<String, IEventCondition>();
	
	public static ImmutableMap<String, IEventAction> getActions() {
		return ImmutableMap.copyOf(actions);
	}
	
	public static ImmutableMap<String, IEventTrigger> getTriggers() {
		return ImmutableMap.copyOf(triggers);
	}
	
	public static ImmutableMap<String, IEventCondition> getConditions() {
		return ImmutableMap.copyOf(conditions);
	}
	
	public static void add(String name, IEventAction action) {
		actions.put(name, action);
	}
	
	public static void add(String name, IEventTrigger trigger) {
		triggers.put(name, trigger);
	}
	
	public static void add(String name, IEventCondition argument) {
		conditions.put(name, argument);
	}
	
	public interface IEventAction {
		public void run(EntityLivingBase entity, EventInfo info);
		public void init(EventInfo info);
	}
	
	public interface IEventTrigger {
		public ArrayList<EventInfo> info();
	}
	
	public interface IEventCondition {
		public void run(String option);
	}
}