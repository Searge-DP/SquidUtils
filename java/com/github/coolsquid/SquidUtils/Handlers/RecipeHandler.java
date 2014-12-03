package com.github.coolsquid.SquidUtils.Handlers;

package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class RecipeHandler {

	public static void OreDictRecipeBlock(String input, Block output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, true, new Object[]{
				"G", Character.valueOf('G'), input}));
	}
	
	public static void OreDictRecipeItem(String input, Item output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, true, new Object[]{
				"G", Character.valueOf('G'), input}));
	}
}
