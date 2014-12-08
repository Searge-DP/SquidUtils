package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.init.Items;
import cpw.mods.fml.common.Mod.EventHandler;

public class StackSizeHandler {

	@EventHandler
	public static void PreInit(int PotionStack, int PearlStack) {
		Items.potionitem.setMaxStackSize(PotionStack);
		Items.ender_pearl.setMaxStackSize(PearlStack);
	}
}