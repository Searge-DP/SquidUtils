package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RegistrySearcher {
	
	/**
	 * Checks configs and starts the requested features.
	 */
	
	public static final void start() {
		if (ConfigHandler.allBlocksUnbreakable)
			blockSearch();
		if (ConfigHandler.stackSizeDivider != 0 || ConfigHandler.durabilityDivider != 1 || ConfigHandler.infiniteDurability || ConfigHandler.hardnessMultiplier > 1)
			itemSearch();
	}
	
	private static final float hardnessMultiplier = ConfigHandler.hardnessMultiplier;
	
	/**
	 * Searches blocks and modifies block properties.
	 */
	
	private static final void blockSearch() {
		int a = 0;
		while (a != 4095) {
			if (Block.blockRegistry.getObjectById(a) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(a);
				if (ConfigHandler.allBlocksUnbreakable) {
					block.setBlockUnbreakable();
				}
				if (hardnessMultiplier > 1) {
					float f = block.getBlockHardness(null, 0, 0, 0);
					block.setHardness(f * hardnessMultiplier);
				}
			}
			a++;
		}
	}
	
	/**
	 * Searches items and modifies item properties.
	 */
	
	@SuppressWarnings("deprecation")
	private static final void itemSearch() {
		int a = 0;
		while (a != 32000) {
			if (Item.itemRegistry.getObjectById(a) != null) {
				Item item = (Item) Item.itemRegistry.getObjectById(a);
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
			a++;
		}
	}
}