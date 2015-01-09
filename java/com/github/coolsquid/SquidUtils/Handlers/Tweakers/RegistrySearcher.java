package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

public class RegistrySearcher {

	public static final void start() {
		if (ConfigHandler.allBlocksUnbreakable)
			blockSearch();
		if (ConfigHandler.stackSizeDivider != 0 || ConfigHandler.durabilityDivider != 1 || ConfigHandler.infiniteDurability)
			itemSearch();
	}
	
	private static final void blockSearch() {
		int a = 0;
		while (a != 4095) {
			if (Block.blockRegistry.getObjectById(a) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(a);
				if (ConfigHandler.allBlocksUnbreakable) {
					block.setBlockUnbreakable();
				}
			}
			a++;
		}
	}
	
	@SuppressWarnings("deprecation")
	private static final void itemSearch() {
		int A = 0;
		while (A != 32000) {
			if (Item.itemRegistry.getObjectById(A) != null) {
				Item item = (Item) Item.itemRegistry.getObjectById(A);
				if (ConfigHandler.stackSizeDivider != 0) {
					item.setMaxStackSize(item.getItemStackLimit() / ConfigHandler.stackSizeDivider);
					if (item.getItemStackLimit() < 1) {
						item.setMaxStackSize(1);
					}
				}
				if (ConfigHandler.durabilityDivider != 1 && item.getMaxDamage() != 0) {
					item.setMaxDamage(item.getMaxDamage() / ConfigHandler.durabilityDivider);
					if (item.getMaxDamage() < 1) {
						item.setMaxDamage(1);
					}
				}
				
				if (ConfigHandler.infiniteDurability) {
					item.setMaxDamage(0);
				}
			}
			A++;
		}
	}
}