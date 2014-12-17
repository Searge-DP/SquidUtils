package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;

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
		LogHandler.debug("Stack sizes set.");
	}
	
	public static final void all(int MaxStackSize) {
		int A = 0;
		while (A != 32000) {
			if (Item.itemRegistry.getObjectById(A) != null) {
				Item item = (Item) Item.itemRegistry.getObjectById(A);
				if (item.getItemStackLimit() != 1) {
					item.setMaxStackSize(MaxStackSize);
				}
			}
			A++;
		}
	}
}
