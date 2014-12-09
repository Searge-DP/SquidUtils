package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

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
	public void EntityJoinWorldEvent(EntityJoinWorldEvent EntityJoinWorldEvent) {
		if(EntityJoinWorldEvent.entity instanceof EntityWither) {
			EntityJoinWorldEvent.setCanceled(true);
		}
	}
}