/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.compat;

import net.minecraft.item.ItemStack;
import Reika.RotaryCraft.API.CompactorAPI;
import Reika.RotaryCraft.API.GrinderAPI;
import Reika.RotaryCraft.API.WorktableAPI;
import cpw.mods.fml.common.Loader;

public class RotaryCraftCompat {
	
	private static boolean activated() {
		if (Loader.isModLoaded("RotaryCraft")) {
			return true;
		}
		return false;
	}
	
	public static void addGrindingRecipe(ItemStack input, ItemStack output) {
		if (activated()) {
			GrinderAPI.addRecipe(input, output);
		}
	}
	
	public static void addCompactorRecipe(ItemStack input, ItemStack output, int pressure, int temperature) {
		if (activated()) {
			CompactorAPI.addCompactorRecipe(input, output, pressure, temperature);
		}
	}
	
	public static void addWorktableRecipe(ItemStack output, Object... input) {
		if (activated()) {
			WorktableAPI.addshapelessRecipe(output, input);
		}
	}
}