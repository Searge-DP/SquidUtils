package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */
 
public class RecipeHandler {

	public static void OreDictRecipe(String name) {
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.diamond_block, true, new Object[]{
				"G", Character.valueOf('G'), name}));
	}
}
