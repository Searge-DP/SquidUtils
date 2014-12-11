package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DifficultyHandler {

	@SubscribeEvent
	public void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.HARD && ConfigHandler.getForceDifficulty().equalsIgnoreCase("HARD")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.HARD;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.NORMAL && ConfigHandler.getForceDifficulty().equalsIgnoreCase("NORMAL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.NORMAL;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.EASY && ConfigHandler.getForceDifficulty().equalsIgnoreCase("EASY")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.EASY;
			}
			else if (Minecraft.getMinecraft().gameSettings.difficulty != EnumDifficulty.PEACEFUL && ConfigHandler.getForceDifficulty().equalsIgnoreCase("PEACEFUL")) {
				Minecraft.getMinecraft().gameSettings.difficulty = EnumDifficulty.PEACEFUL;
			}
			Minecraft.getMinecraft().gameSettings.saveOptions();
			LogHandler.debug("Difficulty forced.");
		}
	}
}