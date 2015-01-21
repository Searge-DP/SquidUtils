package com.github.coolsquid.squidutils.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.github.coolsquid.squidlib.registry.Registry;
import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RecipeHandler {
	
	public static final Registry recipesToRemove = new Registry();
	
	/**
	 * Adds chain recipes.
	 */
	
	public static void chainRecipes() {
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet), new Object[] {"IPI","B B", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate), new Object[] {"B B","IPI","IBI", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings), new Object[] {"IPI","B B","I I", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots), new Object[] {"I I","B B", 'I', Items.iron_ingot, 'B', Blocks.stone_button});
		LogHelper.debug("Recipes initialized.");
	}
	
	/**
	 * Removes recipes for all blocks in the recipesToRemove list.
	 */
	
	public static final void removeRecipes() {
		int a = 0;
		int b = 0;
		if (recipesToRemove.size() != 0) {
			LogHelper.info("Removing recipes...");
			while (a < CraftingManager.getInstance().getRecipeList().size()) {
				IRecipe r = (IRecipe) CraftingManager.getInstance().getRecipeList().get(a);
				try {
					while (b < recipesToRemove.size()) {
						String i = (String) recipesToRemove.get(b);
						if (r.getRecipeOutput().getItem().getUnlocalizedName().equals(i)) {
							CraftingManager.getInstance().getRecipeList().remove(a);
						}
						b++;
					}
				} catch (NullPointerException e) {}
				a++;
				b = 0;
			}
			LogHelper.info("Finished recipe removal.");
		}
	}
}