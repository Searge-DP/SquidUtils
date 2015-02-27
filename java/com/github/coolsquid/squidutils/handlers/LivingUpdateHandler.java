/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LivingUpdateHandler {
	
	private static final int size = ConfigHandler.worldSize;
	
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event) {
		Entity e = event.entity;
		if (e instanceof EntityPlayer) {
			if (e.posX > size) {
				e.setPosition(size, e.posY, e.posZ);
			}
			else if (e.posX < -size) {
				e.setPosition(-size, e.posY, e.posZ);
			}
			if (e.posZ > size) {
				e.setPosition(e.posX, e.posY, size);
			}
			else if (e.posZ < -size) {
				e.setPosition(e.posX, e.posY, -size);
			}
		}
	}
}
