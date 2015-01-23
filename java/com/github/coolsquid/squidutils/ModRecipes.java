package com.github.coolsquid.squidutils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void chain() {
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet), new Object[] {"IPI","B B", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate), new Object[] {"B B","IPI","IBI", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings), new Object[] {"IPI","B B","I I", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots), new Object[] {"I I","B B", 'I', Items.iron_ingot, 'B', Blocks.stone_button});
	}
}