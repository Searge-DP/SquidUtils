/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SpeedHandler {
	
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (ConfigHandler.walkSpeed != -1) {
				((EntityPlayer) event.entity).capabilities.setPlayerWalkSpeed(ConfigHandler.walkSpeed);
			}
			if (ConfigHandler.flySpeed != 1) {
				((EntityPlayer) event.entity).capabilities.setFlySpeed(ConfigHandler.flySpeed);
			}
		}
	}
}