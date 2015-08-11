/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DifficultyHandler {

	public static class DifficultyForcer {

		public static EnumDifficulty difficulty;

		@SubscribeEvent
		public void event(LivingUpdateEvent event) {
			if (event.entity instanceof EntityPlayer) {
				if (!Minecraft.getMinecraft().theWorld.getWorldInfo().getGameType().isCreative() && Minecraft.getMinecraft().gameSettings.difficulty != difficulty) {
					Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
				}
			}
		}
	}

	public static class HardcoreForcer {

		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			event.world.getWorldInfo().hardcore = true;
		}
	}

	public static class CheatForcer {

		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			event.world.getWorldInfo().allowCommands = false;
		}
	}
}