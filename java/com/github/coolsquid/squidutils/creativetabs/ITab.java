package com.github.coolsquid.squidutils.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * @since 1.1.4
 */

public class ITab extends CreativeTabs {
	
	private Item it;
	
	public ITab(String label, Item icon) {
		super(label);
		it = icon;
	}
	
	@Override
	public Item getTabIconItem() {
		return it;
	}
	
	public static final void add(Object[] items, CreativeTabs tab) {
		int a = 0;
		try {
			while (items[a] != null) {
				if (items[a] instanceof Item)
					((Item) items[a]).setCreativeTab(tab);
				if (items[a] instanceof Block)
					((Block) items[a]).setCreativeTab(tab);
				a++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	public static final void remove(Object[] items) {
		int a = 0;
		try {
			while (items[a] != null) {
				if (items[a] instanceof Item)
					((Item) items[a]).setCreativeTab(null);
				if (items[a] instanceof Block)
					((Block) items[a]).setCreativeTab(null);
				a++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
}
