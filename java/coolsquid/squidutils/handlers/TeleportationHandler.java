/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ModConfigHandler.INSTANCE.disableTeleportation) {
			event.setCanceled(true);
		}
	}
}