package com.github.coolsquid.SquidCore.CreativeTabs;

import net.minecraft.creativetab.CreativeTabs;
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
public class SpawnEggTab extends CreativeTabs {
	
	private static CreativeTabs tabSpawnEggs = new SpawnEggTab("tabSpawnEggs");
	
	SpawnEggTab(String tabSpawnEggs)
	{
	super(tabSpawnEggs);
	}

	@Override
	public Item getTabIconItem()
	{
	return Items.spawn_egg;
	}
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		Items.spawn_egg.setCreativeTab(SpawnEggTab.tabSpawnEggs);
	}
}
