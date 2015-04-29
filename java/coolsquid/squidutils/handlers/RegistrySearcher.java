/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import coolsquid.squidutils.config.ModConfigHandler;

public class RegistrySearcher {
	
	/**
	 * Checks configs and starts the requested features.
	 */
	
	public static final void start() {
		if (ModConfigHandler.INSTANCE.allBlocksUnbreakable || ModConfigHandler.INSTANCE.hardnessMultiplier > 1 || ModConfigHandler.INSTANCE.minHardness > 0)
			blockSearch();
		if (ModConfigHandler.INSTANCE.stackSizeDivider != 0 || ModConfigHandler.INSTANCE.durabilityDivider != 1 || ModConfigHandler.INSTANCE.infiniteDurability)
			itemSearch();
	}
	
	/**
	 * Searches blocks and modifies block properties.
	 */
	
	private static void blockSearch() {
		for (int a = 0; a != 4095; a++) {
			if (Block.blockRegistry.getObjectById(a) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(a);
				if (ModConfigHandler.INSTANCE.allBlocksUnbreakable) {
					block.setBlockUnbreakable();
				}
				if (ModConfigHandler.INSTANCE.hardnessMultiplier > 1) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					block.setHardness(f * ModConfigHandler.INSTANCE.hardnessMultiplier);
				}
				if (ModConfigHandler.INSTANCE.minHardness > 0) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					if (f < ModConfigHandler.INSTANCE.minHardness) {
						f = ModConfigHandler.INSTANCE.minHardness;
					}
				}
			}
		}
	}
	
	/**
	 * Searches items and modifies item properties.
	 */
	
	@SuppressWarnings("deprecation")
	private static void itemSearch() {
		for (int a = 0; a != 32000; a++) {
			if (Item.itemRegistry.getObjectById(a) != null) {
				Item item = (Item) Item.itemRegistry.getObjectById(a);
				if (ModConfigHandler.INSTANCE.stackSizeDivider != 0) {
					item.setMaxStackSize(item.getItemStackLimit() / ModConfigHandler.INSTANCE.stackSizeDivider);
					if (item.getItemStackLimit() < 1) {
						item.setMaxStackSize(1);
					}
				}
				if (ModConfigHandler.INSTANCE.durabilityDivider != 1 && item.getMaxDamage() != 0) {
					item.setMaxDamage(item.getMaxDamage() / ModConfigHandler.INSTANCE.durabilityDivider);
					if (item.getMaxDamage() < 1) {
						item.setMaxDamage(1);
					}
				}
				if (ModConfigHandler.INSTANCE.infiniteDurability) {
					item.setMaxDamage(0);
				}
			}
		}
	}
}