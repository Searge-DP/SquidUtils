/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat;

import net.minecraftforge.common.MinecraftForge;
import squeek.applecore.api.hunger.ExhaustionEvent;
import coolsquid.squidutils.config.GeneralConfigHandler;
import coolsquid.squidutils.handlers.FoodHandler;
import coolsquid.squidutils.handlers.PlantHandler;
import coolsquid.squidutils.handlers.RegenHandler;
import coolsquid.squidutils.scripting.ScriptHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AppleCoreCompat {
	
	public static void init() {
		if (GeneralConfigHandler.INSTANCE.starvationDamage != 1.0F || ScriptHandler.INSTANCE.onStarve) {
			MinecraftForge.EVENT_BUS.register(new FoodHandler());
		}
		if (GeneralConfigHandler.INSTANCE.noPlantGrowth) {
			MinecraftForge.EVENT_BUS.register(new PlantHandler());
		}
		if (GeneralConfigHandler.INSTANCE.noHungerRegen || ScriptHandler.INSTANCE.onHungerRegen) {
			MinecraftForge.EVENT_BUS.register(new RegenHandler());
		}
		if (GeneralConfigHandler.INSTANCE.exhaustionMultiplier != 1.0F) {
			MinecraftForge.EVENT_BUS.register(new ExhaustionHandler());
		}
	}

	public static class ExhaustionHandler {

		@SubscribeEvent
		public void onExhaustion(ExhaustionEvent.Exhausted event) {
			event.deltaExhaustion *= GeneralConfigHandler.INSTANCE.exhaustionMultiplier;
		}
	}
}