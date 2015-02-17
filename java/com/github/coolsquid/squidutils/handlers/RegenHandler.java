/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import squeek.applecore.api.hunger.HealthRegenEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void event(HealthRegenEvent event) {
		if (ConfigHandler.noHungerRegen) event.setCanceled(true);
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.player);
			if (a.shouldCancel()) event.setCanceled(true);
		}
	}
}