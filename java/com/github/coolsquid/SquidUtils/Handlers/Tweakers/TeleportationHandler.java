package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler {
	
	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		event.setCanceled(true);
	}
}