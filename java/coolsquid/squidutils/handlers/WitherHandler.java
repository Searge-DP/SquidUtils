/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WitherHandler {
	
	@SubscribeEvent
	public final void event(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityWither) {
			event.setCanceled(true);
		}
	}
}