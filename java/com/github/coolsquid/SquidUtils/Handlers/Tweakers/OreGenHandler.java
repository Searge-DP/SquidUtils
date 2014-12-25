package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OreGenHandler {
	
	@SubscribeEvent
	public void e(OreGenEvent.GenerateMinable event) {
		if (event.type.equals(EventType.IRON)) {
			event.setResult(Result.DENY);
		}
	}
}