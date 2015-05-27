/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.village;

import java.util.Collection;
import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class TradeHandler implements IVillageTradeHandler {

	private final Collection<MerchantRecipe> recipeList;

	public TradeHandler(Collection<MerchantRecipe> recipeList) {
		this.recipeList = recipeList;
	}

	public Collection<MerchantRecipe> getRecipeList() {
		return this.recipeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		recipeList.addAll(this.recipeList);
	}
}