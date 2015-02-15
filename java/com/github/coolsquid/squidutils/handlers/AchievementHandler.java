/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.AchievementEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public final void event(AchievementEvent event) {
		if (ConfigHandler.noAchievements) event.setCanceled(true);
		EventEffectHelper.performEffects(info, event.entityLiving);
		if (info.shouldCancel()) event.setCanceled(true);
	}
}