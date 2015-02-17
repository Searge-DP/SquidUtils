package com.github.coolsquid.squidutils.api;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;

import com.github.coolsquid.squidutils.util.EventInfo;

public class ScriptingAPI {
	
	public static final HashMap<String, IEventAction> actions = new HashMap<String, IEventAction>();
	public static final HashMap<String, IEventTrigger> triggers = new HashMap<String, IEventTrigger>();
	public static final HashMap<String, IEventArgument> arguments = new HashMap<String, IEventArgument>();
	
	public static void add(String name, IEventAction action) {
		actions.put(name, action);
	}
	
	public static void add(String name, IEventTrigger trigger) {
		triggers.put(name, trigger);
	}
	
	public static void add(String name, IEventArgument argument) {
		arguments.put(name, argument);
	}
	
	public interface IEventAction {
		public void run(EntityLivingBase entity, EventInfo info);
		public void init(EventInfo info);
	}
	
	public interface IEventTrigger {
		public ArrayList<EventInfo> info();
	}
	
	public interface IEventArgument {
		public void run(String option);
	}
}