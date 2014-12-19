package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.item.Item;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

public class ItemSearcher {
	
	public static final void search(int stackdivider, int divider) {
		int A = 0;
		while (A != 32000) {
			if (Item.itemRegistry.getObjectById(A) != null) {
				Item item = (Item) Item.itemRegistry.getObjectById(A);
				if (ConfigHandler.getMaxStackSize() != 0) {
					item.setMaxStackSize(item.getItemStackLimit() / stackdivider);
					if (item.getItemStackLimit() < 1) {
						item.setMaxStackSize(1);
					}
				}
				if (ConfigHandler.getDurabilityDivider() != 1 && item.getMaxDamage() != 0) {
					item.setMaxDamage(item.getMaxDamage() / divider);
					if (item.getMaxDamage() < 1) {
						item.setMaxDamage(1);
					}
				}
			}
			A++;
		}
	}
}