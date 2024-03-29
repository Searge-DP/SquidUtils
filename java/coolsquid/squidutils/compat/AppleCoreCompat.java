/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat;

import net.minecraftforge.common.MinecraftForge;
import squeek.applecore.api.hunger.ExhaustionEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AppleCoreCompat {

	public static void init() {
		if (ModConfigHandler.INSTANCE.starvationDamage != 1.0F) {
			MinecraftForge.EVENT_BUS.register(new FoodHandler());
		}
		if (ModConfigHandler.INSTANCE.noPlantGrowth) {
			MinecraftForge.EVENT_BUS.register(new PlantHandler());
		}
		if (ModConfigHandler.INSTANCE.noHungerRegen) {
			MinecraftForge.EVENT_BUS.register(new RegenHandler());
		}
		if (ModConfigHandler.INSTANCE.exhaustionMultiplier != 1.0F) {
			MinecraftForge.EVENT_BUS.register(new ExhaustionHandler());
		}
	}

	public static class ExhaustionHandler {

		@SubscribeEvent
		public void onExhaustion(ExhaustionEvent.Exhausted event) {
			event.deltaExhaustion *= ModConfigHandler.INSTANCE.exhaustionMultiplier;
		}
	}
}