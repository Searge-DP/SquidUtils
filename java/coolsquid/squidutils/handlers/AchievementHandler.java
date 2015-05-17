/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.AchievementEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAchievement(AchievementEvent event) {
		if (ModConfigHandler.INSTANCE.noAchievements) {
			event.setCanceled(true);
		}
	}
}