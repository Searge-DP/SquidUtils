package com.github.coolsquid.SquidUtils.CreativeTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class VanillaTab extends CreativeTabs {
	
	public static CreativeTabs tabVanilla = new VanillaTab("tabVanilla");
	
	public VanillaTab(String tabVanilla) {
		super(tabVanilla);
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.command_block);
	}
	
	public static void preInit() {
		Blocks.mob_spawner.setCreativeTab(tabVanilla);
		Blocks.cocoa.setCreativeTab(tabVanilla);
		Blocks.command_block.setCreativeTab(tabVanilla);
		Blocks.dragon_egg.setCreativeTab(tabVanilla);
		Blocks.water.setCreativeTab(tabVanilla);
		Blocks.lava.setCreativeTab(tabVanilla);
		Blocks.fire.setCreativeTab(tabVanilla);
		Blocks.end_portal.setCreativeTab(tabVanilla).setBlockName("end_portal");
		Blocks.flowing_lava.setCreativeTab(tabVanilla).setBlockName("flowing_lava");
		Blocks.flowing_water.setCreativeTab(tabVanilla).setBlockName("flowing_water");
		Blocks.farmland.setCreativeTab(tabVanilla);
		Blocks.brown_mushroom_block.setCreativeTab(tabVanilla).setBlockName("brown_mushroom_block");
		Blocks.red_mushroom_block.setCreativeTab(tabVanilla).setBlockName("red_mushroom_block");
		Items.command_block_minecart.setCreativeTab(tabVanilla);
	}
}