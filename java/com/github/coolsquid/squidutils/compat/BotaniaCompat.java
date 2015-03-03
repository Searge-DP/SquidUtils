package com.github.coolsquid.squidutils.compat;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;

import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.API;
import cpw.mods.fml.common.Loader;

public class BotaniaCompat {
	
	private static boolean hasChecked;
	
	public static boolean checkAPI() {
		if (!Loader.isModLoaded("Botania")) {
			return false;
		}
		if (!hasChecked) {
			String apiversion = Package.getPackage("vazkii.botania.api").getAnnotation(API.class).apiVersion();
			int version = Integer.parseInt(apiversion);
			if (version > 35) {
				LogHelper.warn("The version of Botania API loaded is newer than the version SquidUtils was made with. Problems may occur. Please contact CoolSquid.");
			}
			else if (version < 35) {
				LogHelper.warn("The version of Botania API loaded is older than the version SquidUtils was made with. Problems may occur. Please update Botania.");
			}
			hasChecked = true;
		}
		return true;
	}
	
	public static void registerManaInfusionRecipe(ItemStack output, Object input, int mana) {
		if (checkAPI()) {
			BotaniaAPI.registerManaInfusionRecipe(output, input, mana);
		}
	}
}