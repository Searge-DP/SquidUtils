/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {
	
	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		event.setCanceled(true);
	}
}