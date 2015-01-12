package com.github.coolsquid.SquidUtils.Utils;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class Registrer {
	
	public static final void register(String className) {
		int a = 0;
		try {
			Field[] f = Class.forName(className).getDeclaredFields();
			while (f[a].get(null) != null) {
				if (f[a].get(null) instanceof Block) {
					Block b = (Block) f[a].get(null);
					if (b.getUnlocalizedName().equals("null")) {
						b.setCreativeTab(CreativeTabs.tabBlock);
						GameRegistry.registerBlock(b, b.getUnlocalizedName());
					}
					else {
						b.setBlockName("block" + a);
						b.setCreativeTab(CreativeTabs.tabBlock);
						GameRegistry.registerBlock(b, b.getUnlocalizedName());
					}
				}
				else if (f[a].get(null) instanceof Item) {
					Item i = (Item) f[a].get(null);
					if (i.getUnlocalizedName().equals("null")) {
						i.setCreativeTab(CreativeTabs.tabBlock);
						GameRegistry.registerItem(i, i.getUnlocalizedName());
					}
					else {
						i.setUnlocalizedName("item" + a);
						i.setCreativeTab(CreativeTabs.tabBlock);
						GameRegistry.registerItem(i, i.getUnlocalizedName());
					}
				}
				a++;
			}
		} catch (Exception e) {
			
		}
	}
}