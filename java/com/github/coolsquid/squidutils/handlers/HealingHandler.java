/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.entity.living.LivingHealEvent;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HealingHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event) {
		for (EventInfo a: info) {
			if (EventEffectHelper.isCorrectType(event.entityLiving, a.getEntitytype()) &&
				event.amount < a.getMaxamount() && event.amount > a.getMinamount()) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}