package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.item.Item;

public class ItemSearcher {
	
	@SuppressWarnings("deprecation")
	public static final void search(int MaxStackSize) {
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