/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import squeek.applecore.api.hunger.StarvationEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler {
	
	public static final EventInfo info = new EventInfo();
	
	@SubscribeEvent
	public void event(StarvationEvent.Starve event) {
		event.starveDamage = ConfigHandler.starvationDamage;
		EventEffectHelper.performEffects(info, event.player);
		if (info.shouldCancel()) event.setCanceled(true);
	}
}