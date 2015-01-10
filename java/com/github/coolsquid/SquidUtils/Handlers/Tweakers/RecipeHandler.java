package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;
import com.github.coolsquid.Testy.Registry.ProtectedRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RecipeHandler {
	
	public static final ProtectedRegistry recipesToRemove = new ProtectedRegistry();
	
	public static void chainRecipes() {
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet), new Object[] {"IPI","B B", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate), new Object[] {"B B","IPI","IBI", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings), new Object[] {"IPI","B B","I I", 'I', Items.iron_ingot, 'P', Blocks.stone_pressure_plate, 'B', Blocks.stone_button});
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots), new Object[] {"I I","B B", 'I', Items.iron_ingot, 'B', Blocks.stone_button});
		LogHelper.debug("Recipes initialized.");
	}
	
	public static final void removeRecipes() {
		int a = 0;
		int b = 0;
		while (a < CraftingManager.getInstance().getRecipeList().size()) {
			IRecipe r = (IRecipe) CraftingManager.getInstance().getRecipeList().get(a);
			try {
				while (b < recipesToRemove.size()) {
					Item i = (Item) recipesToRemove.get(b);
					if (r.getRecipeOutput().getItem().equals(i)) {
						CraftingManager.getInstance().getRecipeList().remove(a);
					}
					b++;
				}
			} catch (NullPointerException e) {}
			a++;
			b = 0;
		}
	}
}