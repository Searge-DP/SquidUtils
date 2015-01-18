package com.github.coolsquid.squidutils.handlers;

import java.util.HashMap;

import net.minecraftforge.event.CommandEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandHandler {
	
	public static final HashMap<String, Integer> commandsToDisable = new HashMap<String, Integer>();
	
	public static final void init() {
		int a = 0;
		while (a < ConfigHandler.commandsToDisable.length) {
			commandsToDisable.put(ConfigHandler.commandsToDisable[a], a);
			a++;
		}
	}
	
	@SubscribeEvent
	public void event(CommandEvent event) {
		if (commandsToDisable.containsKey(event.command.getCommandName()))
			event.setCanceled(true);
	}
}