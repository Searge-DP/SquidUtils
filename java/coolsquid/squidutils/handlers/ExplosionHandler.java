/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.world.ExplosionEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExplosionHandler {

	@SubscribeEvent
	public void onExplosionStart(ExplosionEvent.Start event) {
		event.explosion.explosionSize = event.explosion.explosionSize * ModConfigHandler.INSTANCE.explosionSizeMultiplier;
	}
}