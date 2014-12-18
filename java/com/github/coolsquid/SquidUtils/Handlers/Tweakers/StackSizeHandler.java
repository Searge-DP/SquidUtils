package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.init.Items;

import com.github.coolsquid.SquidUtils.Utils.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class StackSizeHandler {

	public static final void some(int PotionStack, int PearlStack) {
		Items.potionitem.setMaxStackSize(PotionStack);
		Items.ender_pearl.setMaxStackSize(PearlStack);
		LogHelper.debug("Stack sizes set.");
	}
}