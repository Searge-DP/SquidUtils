/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.init.Items;

import com.github.coolsquid.squidutils.helpers.LogHelper;

public class StackSizeHandler {

	public static final void some(int PotionStack, int PearlStack) {
		Items.potionitem.setMaxStackSize(PotionStack);
		Items.ender_pearl.setMaxStackSize(PearlStack);
		LogHelper.debug("Stack sizes set.");
	}
}