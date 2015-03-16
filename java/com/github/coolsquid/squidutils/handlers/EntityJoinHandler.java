/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinHandler implements IEventTrigger {
	
	public static final Set<Class<? extends Entity>> disable = Sets.newHashSet();
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (disable.contains(event.entity.getClass())) {
			event.setCanceled(true);
		}
		if (event.entity instanceof EntityLivingBase) {
			for (EventInfo a: info) {
				if (EventEffectHelper.isCorrectType((EntityLivingBase) event.entity, (String) a.values.get("entitytype"))) {
					EventEffectHelper.performEffects(a, (EntityLivingBase) event.entity);
				}
			}
		}
	}
}