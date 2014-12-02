package com.github.coolsquid.SquidUtils.CreativeTabs;

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
public class ColorTab extends CreativeTabs {
	
	public static CreativeTabs tabColor = new ColorTab("tabColor");
	
	ColorTab(String tabColor)
	{
	super(tabColor);
	}

	@Override
	public Item getTabIconItem()
	{
	return Item.getItemFromBlock(Blocks.wool);
	}
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		Blocks.stained_hardened_clay.setCreativeTab(tabColor);
		Blocks.wool.setCreativeTab(tabColor);
		Blocks.stained_glass.setCreativeTab(tabColor);
		Blocks.stained_glass_pane.setCreativeTab(tabColor);
		Blocks.carpet.setCreativeTab(tabColor);
	}
}
