package com.github.coolsquid.SquidUtils.CreativeTabs;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class VanillaTab extends ITab {
	public VanillaTab() {
		super("tabVanilla", Item.getItemFromBlock(Blocks.command_block));
	}
}