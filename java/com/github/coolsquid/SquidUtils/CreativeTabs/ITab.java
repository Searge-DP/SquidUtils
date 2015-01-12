package com.github.coolsquid.SquidUtils.CreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
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
	
	public void addItems(Object[] item) {
		int a = 0;
		try {
			while (item[a] != null) {
				if (item[a] instanceof Item)
					((Item) item[a]).setCreativeTab(this);
				if (item[a] instanceof Block)
					((Block) item[a]).setCreativeTab(this);
				a++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
}