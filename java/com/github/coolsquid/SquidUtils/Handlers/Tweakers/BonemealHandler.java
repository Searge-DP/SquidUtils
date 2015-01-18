package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.entity.player.BonemealEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BonemealHandler {
	
	@SubscribeEvent
	public void event(BonemealEvent event) {
		event.setCanceled(true);
	}
}