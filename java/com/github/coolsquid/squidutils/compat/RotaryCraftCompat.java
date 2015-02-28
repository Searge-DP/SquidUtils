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
import cpw.mods.fml.common.Optional;

public class RotaryCraftCompat {
	
	private static boolean activated() {
		return Loader.isModLoaded("RotaryCraft");
	}
	
	public static void addGrindingRecipe(ItemStack input, ItemStack output) {
		if (activated()) {
			addGrindingRecipe2(input, output);
		}
	}
	
	public static void addCompactorRecipe(ItemStack input, ItemStack output, int pressure, int temperature) {
		if (activated()) {
			addCompactorRecipe2(input, output, pressure, temperature);
		}
	}
	
	public static void addShapedWorktableRecipe(ItemStack output, Object... input) {
		if (activated()) {
			addShapedWorktableRecipe2(output, input);
		}
	}
	
	public static void addShapelessWorktableRecipe(ItemStack output, Object... input) {
		if (activated()) {
			addShapelessWorktableRecipe2(output, input);
		}
	}
	
	@Optional.Method(modid = "RotaryCraft")
	private static void addGrindingRecipe2(ItemStack input, ItemStack output) {
		GrinderAPI.addRecipe(input, output);
	}
	
	@Optional.Method(modid = "RotaryCraft")
	private static void addCompactorRecipe2(ItemStack input, ItemStack output, int pressure, int temperature) {
		CompactorAPI.addCompactorRecipe(input, output, pressure, temperature);
	}
	
	@Optional.Method(modid = "RotaryCraft")
	private static void addShapedWorktableRecipe2(ItemStack output, Object... input) {
		WorktableAPI.addShapedRecipe(output, input);
	}
	
	@Optional.Method(modid = "RotaryCraft")
	private static void addShapelessWorktableRecipe2(ItemStack output, Object... input) {
		WorktableAPI.addshapelessRecipe(output, input);
	}
}