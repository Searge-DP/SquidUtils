package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BonemealHandler {
	
	@SubscribeEvent
	public void event(BonemealEvent event) {
		event.setCanceled(true);
	}
}