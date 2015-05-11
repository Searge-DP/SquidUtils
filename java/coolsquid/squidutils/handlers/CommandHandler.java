/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.CommandEvent;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.command.CommandCustom;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void event(CommandEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("commandname") || a.values.get("commandname").equals(event.command.getCommandName())) {
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