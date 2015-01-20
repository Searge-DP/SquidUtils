package com.github.coolsquid.squidutils.handlers;

import squeek.applecore.api.hunger.HealthRegenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler {
	
	@SubscribeEvent
	public void event(HealthRegenEvent event) {
		event.setCanceled(true);
	}
}