/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DebugHandler {
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.showDebugInfo == true) {
			Minecraft.getMinecraft().gameSettings.showDebugInfo = false;
			LogHelper.debug("Debug screen blocked.");
		}
	}
}