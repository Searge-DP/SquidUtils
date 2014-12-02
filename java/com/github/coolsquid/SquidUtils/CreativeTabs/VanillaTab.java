package com.github.coolsquid.SquidUtils.CreativeTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
public class VanillaTab extends CreativeTabs{
	
	public static CreativeTabs tabVanilla = new VanillaTab("tabVanilla");
	
	VanillaTab(String tabVanilla)
	{
	super(tabVanilla);
	}

	@Override
	public Item getTabIconItem()
	{
	return Item.getItemFromBlock(Blocks.command_block);
	}
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		
		Blocks.mob_spawner.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.command_block.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.dragon_egg.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.water.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.lava.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.fire.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.end_portal.setCreativeTab(VanillaTab.tabVanilla).setBlockName("end_portal");
		
		Blocks.flowing_lava.setCreativeTab(VanillaTab.tabVanilla).setBlockName("flowing_lava");
		
		Blocks.flowing_water.setCreativeTab(VanillaTab.tabVanilla).setBlockName("flowing_water");
		
		Blocks.farmland.setCreativeTab(VanillaTab.tabVanilla);
		
		Blocks.brown_mushroom_block.setCreativeTab(VanillaTab.tabVanilla).setBlockName("brown_mushroom_block");
		
		Blocks.red_mushroom_block.setCreativeTab(VanillaTab.tabVanilla).setBlockName("red_mushroom_block");
		
		Items.command_block_minecart.setCreativeTab(VanillaTab.tabVanilla);
	}
}
