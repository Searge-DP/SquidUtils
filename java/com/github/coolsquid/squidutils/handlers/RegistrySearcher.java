/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.github.coolsquid.squidutils.config.ConfigHandler;

public class RegistrySearcher {
	
	/**
	 * Checks configs and starts the requested features.
	 */
	
	public static final void start() {
		if (ConfigHandler.INSTANCE.allBlocksUnbreakable || ConfigHandler.INSTANCE.hardnessMultiplier > 1 || ConfigHandler.INSTANCE.minHardness > 0)
			blockSearch();
		if (ConfigHandler.INSTANCE.stackSizeDivider != 0 || ConfigHandler.INSTANCE.durabilityDivider != 1 || ConfigHandler.INSTANCE.infiniteDurability)
			itemSearch();
	}
	
	/**
	 * Searches blocks and modifies block properties.
	 */
	
	private static void blockSearch() {
		for (int a = 0; a != 4095; a++) {
			if (Block.blockRegistry.getObjectById(a) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(a);
				if (ConfigHandler.INSTANCE.allBlocksUnbreakable) {
					block.setBlockUnbreakable();
				}
				if (ConfigHandler.INSTANCE.hardnessMultiplier > 1) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					block.setHardness(f * ConfigHandler.INSTANCE.hardnessMultiplier);
				}
				if (ConfigHandler.INSTANCE.minHardness > 0) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					if (f < ConfigHandler.INSTANCE.minHardness) {
						f = ConfigHandler.INSTANCE.minHardness;
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
				if (ConfigHandler.INSTANCE.stackSizeDivider != 0) {
					item.setMaxStackSize(item.getItemStackLimit() / ConfigHandler.INSTANCE.stackSizeDivider);
					if (item.getItemStackLimit() < 1) {
						item.setMaxStackSize(1);
					}
				}
				if (ConfigHandler.INSTANCE.durabilityDivider != 1 && item.getMaxDamage() != 0) {
					item.setMaxDamage(item.getMaxDamage() / ConfigHandler.INSTANCE.durabilityDivider);
					if (item.getMaxDamage() < 1) {
						item.setMaxDamage(1);
					}
				}
				if (ConfigHandler.INSTANCE.infiniteDurability) {
					item.setMaxDamage(0);
				}
			}
		}
	}
}