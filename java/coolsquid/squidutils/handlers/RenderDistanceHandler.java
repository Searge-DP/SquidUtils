/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import coolsquid.squidutils.config.GeneralConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderDistanceHandler {
	
	/**
	 * Forces the render distance setting.
	 */
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks > GeneralConfigHandler.INSTANCE.maxRenderDistance && event.entity instanceof EntityPlayer) {
			Minecraft.getMinecraft().gameSettings.renderDistanceChunks = GeneralConfigHandler.INSTANCE.maxRenderDistance;
			Minecraft.getMinecraft().gameSettings.saveOptions();
		}
	}
}