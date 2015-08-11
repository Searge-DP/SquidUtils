/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat;

import squeek.applecore.api.hunger.StarvationEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler {

	@SubscribeEvent
	public void event(StarvationEvent.Starve event) {
		event.starveDamage = ModConfigHandler.INSTANCE.starvationDamage;
	}
}