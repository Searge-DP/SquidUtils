/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import squeek.applecore.api.plants.PlantGrowthEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlantHandler {
	
	@SubscribeEvent
	public void event(PlantGrowthEvent.GrowthTick event) {
		event.setCanceled(true);
	}
}