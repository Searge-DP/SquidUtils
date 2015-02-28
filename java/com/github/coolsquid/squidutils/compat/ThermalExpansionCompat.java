/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.compat;

import net.minecraft.item.ItemStack;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;

public class ThermalExpansionCompat {
	
	private static final boolean activated() {
		return Loader.isModLoaded("ThermalExpansion");
	}
	
	public static void addPulverizerRecipe(ItemStack input, ItemStack primaryoutput, ItemStack secondaryoutput, int secondarychance, int energy) {
		if (activated()) {
			addPulverizerRecipe2(input, primaryoutput, secondaryoutput, secondarychance, energy);
		}
	}
	
	@Optional.Method(modid = "ThermalExpansion")
	private static void addPulverizerRecipe2(ItemStack input, ItemStack primaryoutput, ItemStack secondaryoutput, int secondarychance, int energy) {
		PulverizerManager.addOreToDustRecipe(energy, input, primaryoutput, secondaryoutput, secondarychance);
	}
}