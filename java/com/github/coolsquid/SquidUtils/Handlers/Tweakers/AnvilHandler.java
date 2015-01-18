package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.AnvilUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AnvilHandler {
	
	@SubscribeEvent
	public void event(AnvilUpdateEvent event) {
		event.setCanceled(true);
	}
}