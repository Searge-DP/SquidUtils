package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.AnvilUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AnvilHandler {
	
	@SubscribeEvent
	public void event(AnvilUpdateEvent event) {
		event.setCanceled(true);
	}
}