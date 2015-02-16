/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DamageHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		for (EventInfo a: info) {
			if (EventEffectHelper.isCorrectType(event.entityLiving, a.getEntitytype()) &&
				event.ammount < a.getMaxamount() && event.ammount > a.getMinamount()) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}