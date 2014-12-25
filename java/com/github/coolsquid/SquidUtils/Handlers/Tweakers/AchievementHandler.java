package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.entity.player.AchievementEvent;

import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class AchievementHandler {
	
	@SubscribeEvent
	public final void BlockAchievement(AchievementEvent Achievement) {
		Achievement.setCanceled(true);
		LogHelper.debug("Achievement blocked.");
	}
}
