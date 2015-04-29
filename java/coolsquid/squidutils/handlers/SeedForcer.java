/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.world.WorldEvent.Load;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SeedForcer {

	@SubscribeEvent
	public void onWorldLoad(Load event) {
		event.world.getWorldInfo().randomSeed = ModConfigHandler.INSTANCE.defaultSeed;
	}
}