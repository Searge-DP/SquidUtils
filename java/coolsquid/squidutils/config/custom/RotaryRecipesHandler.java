/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import Reika.RotaryCraft.API.CompactorAPI;
import Reika.RotaryCraft.API.GrinderAPI;
import Reika.RotaryCraft.API.WorktableAPI;
import coolsquid.squidutils.config.custom.RotaryRecipesHandler.CompactorRecipeCreationHandler.CompactorRecipe;
import coolsquid.squidutils.config.custom.RotaryRecipesHandler.GrinderRecipeCreationHandler.GrinderRecipe;
import coolsquid.squidutils.config.custom.RotaryRecipesHandler.GrinderSeedCreationHandler.GrinderSeed;


public abstract class RotaryRecipesHandler<E> extends CreationHandler<E> {

	public RotaryRecipesHandler(String dir, Class<E> type) {
		super("rotarycraft/" + dir, type);
	}

	public static class CompactorRecipeCreationHandler extends RotaryRecipesHandler<CompactorRecipe> {

		public static final CompactorRecipeCreationHandler INSTANCE = new CompactorRecipeCreationHandler();

		public CompactorRecipeCreationHandler() {
			super("compactor", CompactorRecipe.class);
		}

		@Override
		protected void handle(CompactorRecipe fromJson) {
			CompactorAPI.addCompactorRecipe(fromJson.input, fromJson.output, fromJson.pressure, fromJson.temperature);
		}

		static class CompactorRecipe {
			public int temperature;
			public int pressure;
			public ItemStack output;
			public ItemStack input;
		}
	}

	public static abstract class WorktableRecipeCreationHandler<E extends IRecipe> extends RotaryRecipesHandler<E> {

		public WorktableRecipeCreationHandler(String dir, Class<E> type) {
			super("worktable/" + dir, type);
		}

		@Override
		protected void handle(E fromJson) {
			WorktableAPI.addRecipe(fromJson);
		}

		public static class WorktableShapedRecipeCreationHandler extends WorktableRecipeCreationHandler<ShapedRecipes> {

			public static final WorktableShapedRecipeCreationHandler INSTANCE = new WorktableShapedRecipeCreationHandler();

			public WorktableShapedRecipeCreationHandler() {
				super("shaped", ShapedRecipes.class);
			}
		}

		public static class WorktableShapelessRecipeCreationHandler extends WorktableRecipeCreationHandler<ShapelessRecipes> {

			public static final WorktableShapelessRecipeCreationHandler INSTANCE = new WorktableShapelessRecipeCreationHandler();

			public WorktableShapelessRecipeCreationHandler() {
				super("shapeless", ShapelessRecipes.class);
			}
		}
	}

	public static class GrinderSeedCreationHandler extends RotaryRecipesHandler<GrinderSeed> {

		public static final GrinderSeedCreationHandler INSTANCE = new GrinderSeedCreationHandler();

		public GrinderSeedCreationHandler() {
			super("grinder/seed", GrinderSeed.class);
		}

		@Override
		protected void handle(GrinderSeed fromJson) {
			GrinderAPI.addGrindableSeed(fromJson.item, fromJson.factor);
		}

		static class GrinderSeed {
			public ItemStack item;
			public float factor;
		}
	}

	public static class GrinderRecipeCreationHandler extends RotaryRecipesHandler<GrinderRecipe> {

		public static final GrinderRecipeCreationHandler INSTANCE = new GrinderRecipeCreationHandler();

		public GrinderRecipeCreationHandler() {
			super("grinder/recipe", GrinderRecipe.class);
		}

		@Override
		protected void handle(GrinderRecipe fromJson) {
			GrinderAPI.addRecipe(fromJson.input, fromJson.output);
		}

		static class GrinderRecipe {
			public ItemStack input;
			public ItemStack output;
		}
	}
}