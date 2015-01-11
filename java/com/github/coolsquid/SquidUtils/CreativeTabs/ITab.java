package com.github.coolsquid.SquidUtils.CreativeTabs;

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
}