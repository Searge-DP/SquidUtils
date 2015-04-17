/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import coolsquid.squidapi.world.WorldHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class DifficultyHandler {

	public static class DifficultyForcer {

		public static String difficulty;

		@SubscribeEvent
		public void event(LivingUpdateEvent event) {
			if (event.entity instanceof EntityPlayer) {
				if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD && difficulty.equalsIgnoreCase("HARD")) {
					Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.HARD;
					Minecraft.getMinecraft().gameSettings.saveOptions();
				}
				else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.NORMAL && difficulty.equalsIgnoreCase("NORMAL")) {
					Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.NORMAL;
					Minecraft.getMinecraft().gameSettings.saveOptions();
				}
				else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.EASY && difficulty.equalsIgnoreCase("EASY")) {
					Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.EASY;
					Minecraft.getMinecraft().gameSettings.saveOptions();
				}
				else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.PEACEFUL && difficulty.equalsIgnoreCase("PEACEFUL")) {
					Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.PEACEFUL;
					Minecraft.getMinecraft().gameSettings.saveOptions();
				}
			}
		}
	}

	public static class HardcoreForcer {

		@SubscribeEvent
		public void onLoad(PlayerEvent.PlayerLoggedInEvent event) {
			WorldHelper.instance().setHardcore();
		}
	}
}