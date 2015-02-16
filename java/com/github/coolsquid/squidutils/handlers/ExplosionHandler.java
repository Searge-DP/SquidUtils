package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.world.ExplosionEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExplosionHandler {
	
	@SubscribeEvent
	public void onExplosionStart(ExplosionEvent.Start event) {
		if (ConfigHandler.disableExplosions) event.setCanceled(true);
		event.explosion.explosionSize = event.explosion.explosionSize * ConfigHandler.explosionSizeMultiplier;
	}
}