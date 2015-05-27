/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.village;

import java.util.Collection;
import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.village.MerchantRecipeList;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class TradeRemover implements IVillageTradeHandler {

	private final Collection<Item> recipesToRemove;

	public TradeRemover(Item... recipesToRemove) {
		this.recipesToRemove = Sets.newHashSet(recipesToRemove);
	}

	public TradeRemover(Collection<Item> recipesToRemove) {
		this.recipesToRemove = recipesToRemove;
	}

	public Collection<Item> getRecipesToRemove() {
		return this.recipesToRemove;
	}

	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		recipeList.clear();
		/*for (int a = 0; a < recipeList.size(); a++) {
			MerchantRecipe recipe = (MerchantRecipe) recipeList.get(a);
			if (this.recipesToRemove.contains(recipe.getItemToSell().getItem())) {
				recipeList.remove(a);
			}
		}*/
	}
}