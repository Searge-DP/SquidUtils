/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityLivingBase) {
			for (EventInfo a: info) {
				if (EventEffectHelper.isCorrectType((EntityLivingBase) event.entity, a.getEntitytype())) {
					EventEffectHelper.performEffects(a, (EntityLivingBase) event.entity);
				}
			}
		}
	}
}