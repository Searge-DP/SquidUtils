package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;

import net.minecraftforge.event.entity.player.AchievementEvent;
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
		LogHandler.debug("Achievement blocked.");
	}
}
