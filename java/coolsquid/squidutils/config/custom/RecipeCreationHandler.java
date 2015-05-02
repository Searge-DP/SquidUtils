/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class RecipeCreationHandler extends CustomContentHandler<ShapedRecipes> {

	public static final RecipeCreationHandler INSTANCE = new RecipeCreationHandler();

	public RecipeCreationHandler() {
		super("recipes", ShapedRecipes.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(ShapedRecipes fromJson) {
		CraftingManager.getInstance().getRecipeList().add(fromJson);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ShapedRecipeDeserializer(), ShapedRecipes.class);
	}

	public static class ShapedRecipeDeserializer implements JsonDeserializer<ShapedRecipes> {

		@Override
		public ShapedRecipes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			ItemStack[] items = new ItemStack[9];
			for (int a = 0; a < o.get("input").getAsJsonArray().size(); a++) {
				items[a] = new ItemStack((Item) Item.itemRegistry.getObject(o.get("input").getAsJsonArray().get(a).getAsString()));
			}
			return new ShapedRecipes(3, 3, items, new ItemStack((Item) Item.itemRegistry.getObject(o.get("outputItem").getAsString()), o.get("outputAmount").getAsInt()));
		}
	}
}