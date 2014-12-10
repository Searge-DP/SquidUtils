package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;

import net.minecraft.init.Items;
import cpw.mods.fml.common.Mod.EventHandler;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class StackSizeHandler {

	@EventHandler
	public static void PreInit(int PotionStack, int PearlStack) {
		Items.potionitem.setMaxStackSize(PotionStack);
		Items.ender_pearl.setMaxStackSize(PearlStack);
		LogHandler.debug("Stack sizes set.");
	}
}