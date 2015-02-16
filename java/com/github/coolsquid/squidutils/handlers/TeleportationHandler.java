/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ConfigHandler.disableTeleportation) event.setCanceled(true);
		for (EventInfo a: info) {
			if (EventEffectHelper.isCorrectType(event.entityLiving, a.getEntitytype())) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}