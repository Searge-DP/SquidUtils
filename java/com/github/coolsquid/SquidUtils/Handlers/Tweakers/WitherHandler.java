package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;

import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class WitherHandler {
	
	@SubscribeEvent
	public void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityWither) {
			event.setCanceled(true);
		}
		LogHandler.debug("Wither blocked.");
	}
}