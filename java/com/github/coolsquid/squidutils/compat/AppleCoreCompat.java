/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.compat;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.handlers.FoodHandler;
import com.github.coolsquid.squidutils.handlers.PlantHandler;
import com.github.coolsquid.squidutils.handlers.RegenHandler;
import com.github.coolsquid.squidutils.handlers.ScriptHandler;

public class AppleCoreCompat {
	
	public static final void init() {
		if (ConfigHandler.starvationDamage < 0 || ScriptHandler.onStarve) {
			MinecraftForge.EVENT_BUS.register(new FoodHandler());
		}
		if (ConfigHandler.noPlantGrowth) {
			MinecraftForge.EVENT_BUS.register(new PlantHandler());
		}
		if (ConfigHandler.noHungerRegen || ScriptHandler.onHungerRegen) {
			MinecraftForge.EVENT_BUS.register(new RegenHandler());
		}
	}
}