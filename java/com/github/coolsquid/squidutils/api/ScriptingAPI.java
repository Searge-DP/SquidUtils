package com.github.coolsquid.squidutils.api;

import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;

import com.github.coolsquid.squidutils.util.EventInfo;

public class ScriptingAPI {
	
	public static final HashMap<String, IEventAction> actions = new HashMap<String, IEventAction>();
	public static final HashMap<String, IEventTrigger> triggers = new HashMap<String, IEventTrigger>();
	public static final HashMap<String, IEventOption> options = new HashMap<String, IEventOption>();
	
	public static void addAction(String name, IEventAction action) {
		actions.put(name, action);
	}
	
	public static void addTrigger(String name, IEventTrigger trigger) {
		triggers.put(name, trigger);
	}
	
	public static void addOption(String name, IEventOption option) {
		options.put(name, option);
	}
	
	public interface IEventAction {
		public void run(EntityLivingBase entity, EventInfo info);
		public void init(EventInfo info);
	}
	
	public interface IEventTrigger {
		public EventInfo info();
	}
	
	public interface IEventOption {
		public void run(String option);
	}
}