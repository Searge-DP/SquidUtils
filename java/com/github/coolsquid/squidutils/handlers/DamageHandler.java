/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.github.coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DamageHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("maxamount") || !a.values.containsKey("minamount") || (event.ammount < (float) a.values.get("maxamount") && event.ammount > (float) a.values.get("minamount"))) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}