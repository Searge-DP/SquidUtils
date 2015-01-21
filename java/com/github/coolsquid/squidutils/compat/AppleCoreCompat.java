package com.github.coolsquid.squidutils.compat;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.handlers.FoodHandler;
import com.github.coolsquid.squidutils.handlers.PlantHandler;
import com.github.coolsquid.squidutils.handlers.RegenHandler;


public class AppleCoreCompat {
	
	public static final void init() {
		if (ConfigHandler.starvationDamage < 0) {
			MinecraftForge.EVENT_BUS.register(new FoodHandler());
		}
		if (ConfigHandler.noPlantGrowth) {
			MinecraftForge.EVENT_BUS.register(new PlantHandler());
		}
		if (ConfigHandler.noHungerRegen) {
			MinecraftForge.EVENT_BUS.register(new RegenHandler());
		}
	}
}