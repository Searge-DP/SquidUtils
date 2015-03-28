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

public class DifficultyHandler {
	
	public static String difficulty = null;
	
	/**
	 * Changes the difficulty setting.
	 */
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (difficulty != null && event.entity instanceof EntityPlayer) {
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
			else if (difficulty.equalsIgnoreCase("HARDCORE") && !WorldHelper.instance().isHardcore()) {
				WorldHelper.instance().setHardcore();
			}
		}
	}
}
