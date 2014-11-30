package com.github.coolsquid.SquidCore.CreativeTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

@SideOnly(Side.CLIENT)
public class WoodTab extends CreativeTabs {
	
	public static CreativeTabs tabWood = new WoodTab("tabWood");
	
	WoodTab(String tabWood)
	{
	super(tabWood);
	}

	@Override
	public Item getTabIconItem()
	{
	return Item.getItemFromBlock(Blocks.log);
	}
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		Blocks.log.setCreativeTab(tabWood);
		Blocks.log2.setCreativeTab(tabWood);
		Blocks.planks.setCreativeTab(tabWood);
		Blocks.oak_stairs.setCreativeTab(tabWood);
		Blocks.spruce_stairs.setCreativeTab(tabWood);
		Blocks.birch_stairs.setCreativeTab(tabWood);
		Blocks.jungle_stairs.setCreativeTab(tabWood);
		Blocks.acacia_stairs.setCreativeTab(tabWood);
		Blocks.dark_oak_stairs.setCreativeTab(tabWood);
		Blocks.wooden_slab.setCreativeTab(tabWood);
		Blocks.wooden_door.setCreativeTab(tabWood);
		Blocks.bookshelf.setCreativeTab(tabWood);
		Blocks.chest.setCreativeTab(tabWood);
		Blocks.trapped_chest.setCreativeTab(tabWood);
		Blocks.crafting_table.setCreativeTab(tabWood);
		Blocks.ladder.setCreativeTab(tabWood);
		Blocks.fence.setCreativeTab(tabWood);
		Blocks.fence_gate.setCreativeTab(tabWood);
	}
}
