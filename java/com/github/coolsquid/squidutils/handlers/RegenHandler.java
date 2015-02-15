/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import squeek.applecore.api.hunger.HealthRegenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler {
	
	public static final EventInfo info = new EventInfo();
	
	@SubscribeEvent
	public void event(HealthRegenEvent event) {
		if (ConfigHandler.noHungerRegen) event.setCanceled(true);
		EventEffectHelper.performEffects(info, event.player);
		if (info.shouldCancel()) event.setCanceled(true);
	}
}