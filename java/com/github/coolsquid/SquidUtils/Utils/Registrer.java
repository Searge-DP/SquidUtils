package com.github.coolsquid.SquidUtils.Utils;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * @since 1.1.4
 *
 */

public class Registrer {
	
	/**
	 * Automatically registers all items/blocks in a class.
	 * @param className
	 */
	
	public static final void register(String className) {
		int a = 0;
		try {
			Field[] f = Class.forName(className).getFields();
			while (f[a].get(null) != null) {
				if (f[a].get(null) instanceof Block) {
					Block b = (Block) f[a].get(null);
					b.setBlockName("block" + a);
					b.setCreativeTab(CreativeTabs.tabBlock);
					GameRegistry.registerBlock(b, b.getUnlocalizedName());
				}
				else if (f[a].get(null) instanceof Item) {
					Item i = (Item) f[a].get(null);
					i.setUnlocalizedName("item" + a);
					i.setCreativeTab(CreativeTabs.tabDecorations);
					GameRegistry.registerItem(i, i.getUnlocalizedName());
				}
				a++;
			}
		} catch (Exception e) {
			
		}
	}
}