package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Handlers.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderDistanceHandler {
	
	@SubscribeEvent
	public void event(LivingUpdateEvent event) {
		if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks > ConfigHandler.MaxRenderDistance && event.entity instanceof EntityPlayer) {
			Minecraft.getMinecraft().gameSettings.renderDistanceChunks = ConfigHandler.MaxRenderDistance;
			Minecraft.getMinecraft().gameSettings.saveOptions();
		}
	}
}