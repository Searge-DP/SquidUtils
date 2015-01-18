package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.entity.player.UseHoeEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolHandler {
	
	@SubscribeEvent
	public void hoeEvent(UseHoeEvent event) {
		event.setCanceled(true);
	}
}