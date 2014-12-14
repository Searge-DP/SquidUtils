package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class VillagerHandler {
	
	@SubscribeEvent
	public void hurt(LivingHurtEvent event) {
		if (event.entity instanceof EntityVillager) {
			event.setCanceled(true);
		}
	}
}