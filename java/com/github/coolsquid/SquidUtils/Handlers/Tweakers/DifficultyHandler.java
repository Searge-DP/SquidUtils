package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DifficultyHandler {
	
	private static final String difficulty = ConfigHandler.forceDifficulty;
	
	/**
	 * Changes the difficulty setting.
	 */
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD && difficulty.equalsIgnoreCase("HARD")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.HARD;
				Minecraft.getMinecraft().gameSettings.saveOptions();
				LogHelper.debug("Difficulty forced.");
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.NORMAL && difficulty.equalsIgnoreCase("NORMAL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.NORMAL;
				Minecraft.getMinecraft().gameSettings.saveOptions();
				LogHelper.debug("Difficulty forced.");
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.EASY && difficulty.equalsIgnoreCase("EASY")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.EASY;
				Minecraft.getMinecraft().gameSettings.saveOptions();
				LogHelper.debug("Difficulty forced.");
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.PEACEFUL && difficulty.equalsIgnoreCase("PEACEFUL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.PEACEFUL;
				Minecraft.getMinecraft().gameSettings.saveOptions();
				LogHelper.debug("Difficulty forced.");
			}
		}
	}
}