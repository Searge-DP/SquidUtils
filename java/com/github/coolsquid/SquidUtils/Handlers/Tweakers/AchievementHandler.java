package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.entity.player.AchievementEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {
	
	@SubscribeEvent
	public void BlockAchievement(AchievementEvent Achievement) {
		Achievement.setCanceled(true);
	}
}
