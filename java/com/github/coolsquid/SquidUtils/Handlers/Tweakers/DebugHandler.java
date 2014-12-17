package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DebugHandler {
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.showDebugInfo == true) {
			Minecraft.getMinecraft().gameSettings.showDebugInfo = false;
			LogHelper.debug("Debug screen blocked.");
		}
	}
}