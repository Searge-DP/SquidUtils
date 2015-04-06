/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import coolsquid.squidutils.config.GeneralConfigHandler;

public class RegistrySearcher {
	
	/**
	 * Checks configs and starts the requested features.
	 */
	
	public static final void start() {
		if (GeneralConfigHandler.INSTANCE.allBlocksUnbreakable || GeneralConfigHandler.INSTANCE.hardnessMultiplier > 1 || GeneralConfigHandler.INSTANCE.minHardness > 0)
			blockSearch();
		if (GeneralConfigHandler.INSTANCE.stackSizeDivider != 0 || GeneralConfigHandler.INSTANCE.durabilityDivider != 1 || GeneralConfigHandler.INSTANCE.infiniteDurability)
			itemSearch();
	}
	
	/**
	 * Searches blocks and modifies block properties.
	 */
	
	private static void blockSearch() {
		for (int a = 0; a != 4095; a++) {
			if (Block.blockRegistry.getObjectById(a) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(a);
				if (GeneralConfigHandler.INSTANCE.allBlocksUnbreakable) {
					block.setBlockUnbreakable();
				}
				if (GeneralConfigHandler.INSTANCE.hardnessMultiplier > 1) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					block.setHardness(f * GeneralConfigHandler.INSTANCE.hardnessMultiplier);
				}
				if (GeneralConfigHandler.INSTANCE.minHardness > 0) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					if (f < GeneralConfigHandler.INSTANCE.minHardness) {
						f = GeneralConfigHandler.INSTANCE.minHardness;
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
				if (GeneralConfigHandler.INSTANCE.stackSizeDivider != 0) {
					item.setMaxStackSize(item.getItemStackLimit() / GeneralConfigHandler.INSTANCE.stackSizeDivider);
					if (item.getItemStackLimit() < 1) {
						item.setMaxStackSize(1);
					}
				}
				if (GeneralConfigHandler.INSTANCE.durabilityDivider != 1 && item.getMaxDamage() != 0) {
					item.setMaxDamage(item.getMaxDamage() / GeneralConfigHandler.INSTANCE.durabilityDivider);
					if (item.getMaxDamage() < 1) {
						item.setMaxDamage(1);
					}
				}
				if (GeneralConfigHandler.INSTANCE.infiniteDurability) {
					item.setMaxDamage(0);
				}
			}
		}
	}
}