/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ConfigHandler.disableTeleportation) event.setCanceled(true);
		LogHelper.info("debug1");
		EventEffectHelper.performEffects(info, event.entityLiving);
	}
}