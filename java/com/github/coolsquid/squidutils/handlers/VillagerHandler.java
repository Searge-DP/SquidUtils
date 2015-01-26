/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VillagerHandler {
	
	@SubscribeEvent
	public final void hurt(LivingHurtEvent event) {
		if (event.entity instanceof EntityVillager) {
			event.setCanceled(true);
			LogHelper.debug("Villager protected.");
		}
	}
}