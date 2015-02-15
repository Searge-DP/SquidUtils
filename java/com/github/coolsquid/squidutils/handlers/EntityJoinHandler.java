/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityLivingBase) {
			EventEffectHelper.performEffects(info, (EntityLivingBase) event.entity);
		}
	}
}