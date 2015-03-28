/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderDistanceHandler {
	
	/**
	 * Forces the render distance setting.
	 */
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks > ConfigHandler.INSTANCE.maxRenderDistance && event.entity instanceof EntityPlayer) {
			Minecraft.getMinecraft().gameSettings.renderDistanceChunks = ConfigHandler.INSTANCE.maxRenderDistance;
			Minecraft.getMinecraft().gameSettings.saveOptions();
		}
	}
}