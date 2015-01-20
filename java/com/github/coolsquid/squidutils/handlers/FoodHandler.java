package com.github.coolsquid.squidutils.handlers;

import squeek.applecore.api.hunger.StarvationEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler {
	
	@SubscribeEvent
	public void event(StarvationEvent.Starve event) {
		event.starveDamage = ConfigHandler.starvationDamage;
	}
}