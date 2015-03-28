/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat;

import net.minecraftforge.common.MinecraftForge;
import coolsquid.squidutils.config.ConfigHandler;
import coolsquid.squidutils.handlers.FoodHandler;
import coolsquid.squidutils.handlers.PlantHandler;
import coolsquid.squidutils.handlers.RegenHandler;
import coolsquid.squidutils.scripting.ScriptHandler;

public class AppleCoreCompat {
	
	public static final void init() {
		if (ConfigHandler.INSTANCE.starvationDamage < 0 || ScriptHandler.INSTANCE.onStarve) {
			MinecraftForge.EVENT_BUS.register(new FoodHandler());
		}
		if (ConfigHandler.INSTANCE.noPlantGrowth) {
			MinecraftForge.EVENT_BUS.register(new PlantHandler());
		}
		if (ConfigHandler.INSTANCE.noHungerRegen || ScriptHandler.INSTANCE.onHungerRegen) {
			MinecraftForge.EVENT_BUS.register(new RegenHandler());
		}
	}
}