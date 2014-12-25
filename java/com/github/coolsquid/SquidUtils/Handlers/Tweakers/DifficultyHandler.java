package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DifficultyHandler {

	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD && ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.HARD;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.NORMAL && ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.NORMAL;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.EASY && ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.EASY;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.PEACEFUL && ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.PEACEFUL;
			}
			Minecraft.getMinecraft().gameSettings.saveOptions();
			LogHelper.debug("Difficulty forced.");
		}
	}
}