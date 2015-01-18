package com.github.coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.logging.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RenderDistanceHandler {
	
	/**
	 * Forces the render distance setting.
	 */
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks > ConfigHandler.maxRenderDistance && event.entity instanceof EntityPlayer) {
			Minecraft.getMinecraft().gameSettings.renderDistanceChunks = ConfigHandler.maxRenderDistance;
			Minecraft.getMinecraft().gameSettings.saveOptions();
			LogHelper.debug("Render distance forced.");
		}
	}
}