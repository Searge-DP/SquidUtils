/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import coolsquid.squidutils.config.ConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SpeedHandler {
	
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (ConfigHandler.INSTANCE.walkSpeed != -1) {
				((EntityPlayer) event.entity).capabilities.setPlayerWalkSpeed(ConfigHandler.INSTANCE.walkSpeed);
			}
			if (ConfigHandler.INSTANCE.flySpeed != 1) {
				((EntityPlayer) event.entity).capabilities.setFlySpeed(ConfigHandler.INSTANCE.flySpeed);
			}
		}
	}
}