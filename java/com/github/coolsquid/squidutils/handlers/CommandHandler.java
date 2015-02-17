/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraftforge.event.CommandEvent;

import com.github.coolsquid.squidapi.helpers.server.ServerHelper;
import com.github.coolsquid.squidutils.command.CommandCustom;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	public static final HashSet<String> commandsToDisable = new HashSet<String>();
	
	@SubscribeEvent
	public void event(CommandEvent event) {
		if (commandsToDisable.contains(event.command.getCommandName())) {
			event.setCanceled(true);
		}
		for (EventInfo a: info) {
			if (a.getCommandname().equals(event.command.getCommandName())) {
				if (event.command instanceof CommandCustom) {
					EventEffectHelper.performEffects(a, ServerHelper.getPlayerFromNick(event.parameters[0]));
				}
				else {
					EventEffectHelper.performEffects(a, ServerHelper.getPlayerFromName(event.sender.getCommandSenderName()));
				}
			}
		}
	}
}