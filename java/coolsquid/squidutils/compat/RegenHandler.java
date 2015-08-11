/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat;

import squeek.applecore.api.hunger.HealthRegenEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler {

	@SubscribeEvent
	public void event(HealthRegenEvent.AllowRegen event) {
		if (ModConfigHandler.INSTANCE.noHungerRegen) {
			event.setResult(Result.DENY);
			return;
		}
	}
}