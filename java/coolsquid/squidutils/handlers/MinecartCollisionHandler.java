/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MinecartCollisionHandler {
	
	@SubscribeEvent
	public void onCollide(MinecartCollisionEvent event) {
		if (event.minecart instanceof EntityMinecartTNT) {
			((EntityMinecartTNT) event.minecart).minecartTNTFuse = 5;
		}
	}
}