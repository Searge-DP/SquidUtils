/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import squeek.applecore.api.hunger.StarvationEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void event(StarvationEvent.Starve event) {
		event.starveDamage = ConfigHandler.starvationDamage;
		for (EventInfo a: info) EventEffectHelper.performEffects(a, event.player);
	}
}