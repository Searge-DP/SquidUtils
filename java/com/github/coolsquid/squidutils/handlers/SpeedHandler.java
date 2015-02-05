/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SpeedHandler {
	
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			((EntityPlayer) event.entity).capabilities.setPlayerWalkSpeed(ConfigHandler.walkSpeed);
			((EntityPlayer) event.entity).capabilities.setFlySpeed(ConfigHandler.flySpeed);
		}
	}
}