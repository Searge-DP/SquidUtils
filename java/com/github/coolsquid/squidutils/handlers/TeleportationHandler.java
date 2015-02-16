/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ConfigHandler.disableTeleportation) event.setCanceled(true);
		EventEffectHelper.performEffects(info, event.entityLiving);
	}
}