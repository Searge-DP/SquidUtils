/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.compat;

import net.minecraft.item.ItemStack;
import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cpw.mods.fml.common.Loader;

public class ThermalExpansionCompat {
	
	private static boolean activated() {
		if (Loader.isModLoaded("ThermalExpansion")) {
			return true;
		}
		return false;
	}
	
	public static void addPulverizerRecipe(ItemStack input, ItemStack primaryoutput, ItemStack secondaryoutput, int secondarychance, int energy) {
		if (activated()) {
			PulverizerManager.addRecipe(energy, input, primaryoutput, secondaryoutput, secondarychance);
		}
	}
}