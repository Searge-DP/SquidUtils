package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class WitherHandler {
	
	@SubscribeEvent
	public final void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityWither) {
			event.setCanceled(true);
		}
		LogHelper.debug("Wither blocked.");
	}
}