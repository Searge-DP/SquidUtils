package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.ServerChatEvent;

import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerChatHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		for (EventInfo a: info) {
			if (event.message.contains(a.getChattrigger())) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}