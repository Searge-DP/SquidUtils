package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RenderDistanceHandler {
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks > ConfigHandler.getMaxRenderDistance() && event.entity instanceof EntityPlayer) {
			Minecraft.getMinecraft().gameSettings.renderDistanceChunks = ConfigHandler.getMaxRenderDistance();
			Minecraft.getMinecraft().gameSettings.saveOptions();
			LogHelper.debug("Render distance forced.");
		}
	}
}