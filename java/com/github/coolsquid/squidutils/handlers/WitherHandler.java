/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WitherHandler {
	
	@SubscribeEvent
	public final void event(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityWither) {
			event.setCanceled(true);
			LogHelper.debug("Wither blocked.");
		}
	}
}