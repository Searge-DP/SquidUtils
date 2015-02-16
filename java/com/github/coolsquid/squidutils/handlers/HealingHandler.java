/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import net.minecraftforge.event.entity.living.LivingHealEvent;

import com.github.coolsquid.squidutils.util.DamageEventInfo;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HealingHandler {
	
	public static final EventInfo info = new EventInfo();
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event) {
		HashMap<Object, EventInfo> map = info.getKeytoinfo();
		Set<Entry<Object, EventInfo>> set = map.entrySet();
		for (int a = 0; a < map.size(); a++) {
			Entry<Object, EventInfo> entry = set.iterator().next();
			DamageEventInfo info = (DamageEventInfo) entry.getKey();
			if (info.getMaxamount() == -546314 && info.getMinamount() == -546314) {
				EventInfo info2 = map.get(info);
				EventEffectHelper.performEffects(info2, event.entityLiving);
				if (info2.shouldCancel()) event.setCanceled(true);
				return;
			}
			else if (info.getMaxamount() - info.getMinamount() == info.getMaxamount() - event.amount) {
				EventInfo info2 = map.get(info);
				EventEffectHelper.performEffects(info2, event.entityLiving);
				if (info2.shouldCancel()) event.setCanceled(true);
			}
		}
	}
}