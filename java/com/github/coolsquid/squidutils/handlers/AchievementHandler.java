/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.entity.player.AchievementEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();

	@SubscribeEvent
	public final void event(AchievementEvent event) {
		if (ConfigHandler.noAchievements) event.setCanceled(true);
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.entityLiving);
			if (a.shouldCancel()) event.setCanceled(true);
		}
	}
}