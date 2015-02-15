/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.HashSet;

import net.minecraftforge.event.CommandEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandHandler {
	
	public static final HashSet<String> commandsToDisable = new HashSet<String>();
	
	@SubscribeEvent
	public void event(CommandEvent event) {
		if (commandsToDisable.contains(event.command.getCommandName())) {
			event.setCanceled(true);
		}
	}
}