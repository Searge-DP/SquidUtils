/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.world.ExplosionEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExplosionHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onExplosionStart(ExplosionEvent.Start event) {
		if (ConfigHandler.disableExplosions) event.setCanceled(true);
		event.explosion.explosionSize = event.explosion.explosionSize * ConfigHandler.explosionSizeMultiplier;
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a);
		}
	}
}