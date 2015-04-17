/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VillagerHandler {
	
	@SubscribeEvent
	public final void hurt(LivingHurtEvent event) {
		if (event.entity instanceof EntityVillager) {
			event.setCanceled(true);
		}
	}
}