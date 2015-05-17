/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.player.AchievementEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onAchievement(AchievementEvent event) {
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.entityLiving);
			if (a.values.containsKey("cancel")) {
				event.setCanceled(true);
			}
		}
	}
}