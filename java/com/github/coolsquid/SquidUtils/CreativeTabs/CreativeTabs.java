package com.github.coolsquid.SquidUtils.CreativeTabs;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public class CreativeTabs {
	
	/**
	 * Adds items to creative tabs.
	 */
	
	public static final void preInit() {
		Object[] o = {
				Blocks.mob_spawner,
				Blocks.cocoa,
				Blocks.command_block,
				Blocks.dragon_egg,
				Blocks.water,
				Blocks.lava,
				Blocks.fire,
				Blocks.end_portal,
				Blocks.flowing_lava,
				Blocks.flowing_water,
				Blocks.farmland,
				Blocks.brown_mushroom_block,
				Blocks.red_mushroom_block,
				Items.command_block_minecart,
				};
		ITab.add(o, new VanillaTab());
	}
}