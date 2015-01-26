/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.AchievementEvent;

import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {
	
	@SubscribeEvent
	public final void event(AchievementEvent event) {
		event.setCanceled(true);
		LogHelper.debug("Achievement blocked.");
	}
}