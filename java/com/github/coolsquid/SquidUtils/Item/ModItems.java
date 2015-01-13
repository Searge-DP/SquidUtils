package com.github.coolsquid.SquidUtils.Item;

import net.minecraft.item.ItemStack;

import com.github.coolsquid.SquidUtils.CreativeTabs.CreativeTabs;
import com.github.coolsquid.Testy.Utils.Reflection.ReflectionHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static final IGlassBottle glass_bottle = new IGlassBottle();
	public static final void preInit() {
		CreativeTabs.tabVanilla.add(new Object[] {glass_bottle});
		glass_bottle.setUnlocalizedName("glass_bottle");
		GameRegistry.registerItem(glass_bottle, glass_bottle.getUnlocalizedName());
		ReflectionHelper.replaceField(ModItems.class, "glass_bottle", glass_bottle);
		
		GameRegistry.addRecipe(new ItemStack(net.minecraft.init.Items.glass_bottle, 1),
				new Object[] {"I I"," I", 'I', net.minecraft.init.Items.iron_ingot});
	}
}