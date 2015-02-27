/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.config;

import java.io.File;

import javax.swing.JOptionPane;

import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.squidapi.exception.DO_NOT_REPORT_EXCEPTION;

public class ConfigHandler {
	
	/**
	 * The config file.
	 */
	
	private static File configFile;
	
	/**
	 * The configuration.
	 */
	
	private static Configuration config;
	
	public static final void preInit(File file) {
		configFile = file;
		createConfig();
		initCategories();
		readConfig();
	}
	
	private static final void createConfig() {
		if (config == null)
			config = new Configuration(configFile);
	}
	
	/*
	 * Available categories. DO NOT MODIFY, as it breaks configs.
	 */
	
	/**
	 * For options that doesn't fit elsewhere.
	 */
	
	private static final String CATEGORY_GENERAL = "General";
	
	/**
	 * For intermod compatibility.
	 */
	
	private static final String CATEGORY_COMPAT = "Compatibility";
	
	/**
	 * Options regarding mobs.
	 */
	
	private static final String CATEGORY_MOBS = "Mob options";
	
	/**
	 * Options changing block and item properties.
	 */
	
	private static final String CATEGORY_PROPERTIES = "Block and item properties";
	
	/**
	 * Options regarding Minecraft settings.
	 */
	
	private static final String CATEGORY_GAMESETTINGS = "Force game options";
	
	/**
	 * All options regarding creative tabs.
	 */
	
	private static final String CATEGORY_CREATIVETABS = "Creative tabs";
	
	/**
	 * Modpack specific options.
	 */
	
	private static final String CATEGORY_MODPACKS = "Modpack specific options";
	
	/**
	 * Hunger options. REQUIRES APPLE CORE!
	 */
	
	private static final String CATEGORY_HUNGER = "Hunger options";
	
	/*
	 * Hardcoded options. Modify them as you want to.
	 */
	
	public static boolean debug;
	
	/*
	 * All available config options. Modify them as you want to.
	 */
	
	/**
	 * Forces the specified difficulty. Will crash if set to something else than FALSE, PEACEFUL, EASY, NORMAL or HARD.
	 */
	
	public static String forceDifficulty = "FALSE";
	
	/**
	 * Disables TNT.
	 */
	
	public static boolean noTNT;
	
	/**
	 * Disables achievements.
	 */
	
	public static boolean noAchievements;
	
	/**
	 * Disables the wither.
	 */
	
	public static boolean noWitherBoss;
	
	/**
	 * Sets the max stack size for potions to the specified amount.
	 */
	
	public static int potionStacks = 1;
	
	/**
	 * Adds recipes for chain armor.
	 */
	
	public static boolean chainRecipes;
	
	/**
	 * Disables the debug screen.
	 */
	
	public static boolean noDebug;
	
	/**
	 * Sets the max stack size for ender pearls to the specified amount.
	 */
	
	public static int pearlStack = 16;
	
	/**
	 * Sets the max render distance to the specified amount.
	 */
	
	public static int maxRenderDistance = 16;
	
	/**
	 * Makes TNT drop as an item when it explodes. Requires noTNT to be true.
	 */
	
	public static boolean tntDropItems = true;
	
	/**
	 * Makes villagers unhurtable.
	 */
	
	public static boolean villagerProtection;
	
	/**
	 * Logs all blocks broken and all entities killed.
	 */
	
	public static boolean logStuff;
	
	/**
	 * Divides all stack sizes by the specified amount.
	 */
	
	public static int stackSizeDivider = 0;
	
	/**
	 * Makes all blocks unbreakable.
	 */
	
	public static boolean allBlocksUnbreakable;
	
	/**
	 * Divides all items durability by the specified amount.
	 */
	
	public static int durabilityDivider = 1;
	
	/**
	 * Clears all Vanilla recipes if 1, and clears all recipes if 2.
	 */
	
	public static int clearRecipes = 0;
	
	/**
	 * Adds an extra creative tab for Vanilla stuff.
	 */
	
	public static boolean tabVanilla = true;
	
	/**
	 * Gives all items infinite durability.
	 */
	
	public static boolean infiniteDurability;
	
	/**
	 * Multiplies all blocks hardness by the specified amount.
	 */
	
	public static float hardnessMultiplier = 1;
	
	/**
	 * List of modids. All mods missing will be logged.
	 */
	
	public static String[] modList = new String[] {};

	/**
	 * Disables the Vanilla anvil.
	 */
	
	public static boolean disableAnvil;
	
	/**
	 * Disables enderman and enderpearl teleportation.
	 */
	
	public static boolean disableTeleportation;
	
	/**
	 * Disables bonemeal.
	 */
	
	public static boolean disableBonemeal;
	
	/**
	 * Disables hoes.
	 */
	
	public static boolean disableHoes;
	
	/**
	 * Disables glass bottle interaction.
	 */
	
	public static boolean disableBottleFluidInteraction;
	
	/**
	 * Generates a list of modids in the working directory.
	 */
	
	public static int generateModList = 0;
	
	/**
	 * Extra mods to not warn about.
	 */
	
	public static String[] optionalMods = new String[] {};
	
	/**
	 * Replaces the starvation damage with the specified amount. Set to 0 to disable.
	 */
	
	public static float starvationDamage = 0;
	
	/**
	 * Disables plant growth.
	 */
	
	public static boolean noPlantGrowth;
	
	/**
	 * Disables hunger regen.
	 */
	
	public static boolean noHungerRegen;
	
	public static float walkSpeed = 0.1F;
	public static float flySpeed = 0.05F;

	public static int minHardness = 0;
	
	public static float explosionSizeMultiplier = 1;
	
	public static int worldSize = 0;

	public static boolean explodeTNTMinecartsOnCollide;
	
	/**
	 * Sets category comments.
	 */
	
	private static void initCategories() {
		config.setCategoryComment(CATEGORY_GENERAL, "General options.");
		config.setCategoryComment(CATEGORY_COMPAT, "Compatibility options.");
		config.setCategoryComment(CATEGORY_MOBS, "Mob options.");
		config.setCategoryComment(CATEGORY_PROPERTIES, "Configure block and item properties.");
		config.setCategoryComment(CATEGORY_GAMESETTINGS, "Force game options.");
		config.setCategoryComment(CATEGORY_CREATIVETABS, "Disable or enable creative tabs.");
		config.setCategoryComment(CATEGORY_HUNGER, "Modify hunger options. REQUIRES APPLE CORE!");
	}
	
	/**
	 * Reads the config.
	 */
	
	private static final void readConfig() {
		forceDifficulty = config.getString("forceDifficulty", CATEGORY_GAMESETTINGS, "FALSE", "Forces the specified difficulty. Allows for HARD, NORMAL, EASY, PEACEFUL or FALSE. Set to FALSE to disable.");
		noTNT = config.getBoolean("noTNT", CATEGORY_GENERAL, false, "Stops TNT from exploding.");
		noAchievements = config.getBoolean("noAchievements", CATEGORY_GENERAL, false, "Disables achievements.");
		noWitherBoss = config.getBoolean("noWitherBoss", CATEGORY_GENERAL, false, "Disables the witherboss.");
		potionStacks = config.getInt("maxPotionStackSize", CATEGORY_PROPERTIES, 1, 1, 64, "Sets the max stacksize for potions.");
		chainRecipes = config.getBoolean("chainRecipes", CATEGORY_GENERAL, false, "Makes recipes for all pieces of chain armor.");
		noDebug = config.getBoolean("noDebug", CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		pearlStack = config.getInt("maxEnderPearlStackSize", CATEGORY_PROPERTIES, 16, 1, 64, "Sets the max stacksize for enderpearls.");
		maxRenderDistance = config.getInt("maxRenderDistance", CATEGORY_GAMESETTINGS, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		tntDropItems = config.getBoolean("tntDropItems", CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		villagerProtection = config.getBoolean("villagerProtection", CATEGORY_MOBS, false, "Makes villagers unhurtable.");
		logStuff = config.getBoolean("logStuff", CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		stackSizeDivider = config.getInt("stackSizeDivider", CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		allBlocksUnbreakable = config.getBoolean("allBlocksUnbreakable", CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		durabilityDivider = config.getInt("durabilityDivider", CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		clearRecipes = config.getInt("clearRecipes", CATEGORY_GENERAL, 0, 0, 2, "Clears Vanilla recipes if 1, clears all recipes if 2. Set to 0 to disable. Clearing all recipes will not work if any of Reika's mods are loaded.");
		infiniteDurability = config.getBoolean("infiniteDurability", CATEGORY_PROPERTIES, false, "Makes all items have infinite durability. Overrides \"durabilityDivider\".");
		tabVanilla = config.getBoolean("tabVanilla", CATEGORY_CREATIVETABS, false, "Enables the extra Vanilla stuff creative tab.");
		hardnessMultiplier = config.getFloat("hardnessMultiplier", CATEGORY_PROPERTIES, 1, 1, 100, "Multiplies all blocks hardness by the specified number. Set to 1.0 to disable.");
		modList = config.getStringList("modList", CATEGORY_MODPACKS, new String[] {}, "If any of the mods listed are missing, a warning will be printed to the log.");
		disableAnvil = config.getBoolean("disableAnvil", CATEGORY_GENERAL, false, "Disables the Vanilla anvil.");
		disableTeleportation = config.getBoolean("disableTeleportation", CATEGORY_GENERAL, false, "Disables enderman and enderpearl teleportation.");
		disableBonemeal = config.getBoolean("disableBonemeal", CATEGORY_GENERAL, false, "Disables bonemeal.");
		disableHoes = config.getBoolean("disableHoes", CATEGORY_GENERAL, false, "Disables hoes.");
		disableBottleFluidInteraction = config.getBoolean("disableBottleFluidInteraction", CATEGORY_GENERAL, false, "Disables bottles from working with cauldrons.");
		generateModList = config.getInt("generateModList", CATEGORY_MODPACKS, 0, 0, 2, "Generates a list of modids in the working directory. Set to 1 to generate only modids, or set to 2 to generate modids and versions.");
		optionalMods = config.getStringList("optionalMods", CATEGORY_MODPACKS, new String[] {}, "Extra mods to not warn about if added or removed.");
		starvationDamage = config.getFloat("starvationDamage", CATEGORY_HUNGER, 1, 0, 20, "Modifies the starvation damage.");
		noPlantGrowth = config.getBoolean("noPlantGrowth", CATEGORY_HUNGER, false, "Disables plant growth.");
		noHungerRegen = config.getBoolean("noHungerRegen", CATEGORY_HUNGER, false, "Disables hunger regen.");
		walkSpeed = config.getFloat("walkSpeed", CATEGORY_GENERAL, 0.1F, 0F, 20F, "Sets the players walk speed.");
		flySpeed = config.getFloat("flySpeed", CATEGORY_GENERAL, 0.05F, 0F, 20F, "Sets the players flying speed.");
		minHardness = config.getInt("minHardness", CATEGORY_PROPERTIES, 0, 0, 1080, "Sets the minimum block hardness.");
		explosionSizeMultiplier = config.getFloat("explosionSizeMultiplier", CATEGORY_GENERAL, 1, 0, 1080, "Multiplies the size of all explosions by the specified amount.");
		worldSize = config.getInt("worldSize", CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "Sets the size of the world. Set to 0 to disable.");
		explodeTNTMinecartsOnCollide = config.getBoolean("explodeTNTMinecartsOnCollide", CATEGORY_GENERAL, false, "Explodes minecarts with TNT whenever they collide with an entity.");
		
		String password = config.getString("password", CATEGORY_GENERAL, "", "Sets a password required to launch Minecraft.");
		if (!(password.isEmpty())) {
			try {
				String p = JOptionPane.showInputDialog("password:");
				if (!p.equals(password)) {
					throw new DO_NOT_REPORT_EXCEPTION("Wrong password.");
				}
			}
			catch (NullPointerException e) {
				throw new DO_NOT_REPORT_EXCEPTION("Wrong password.");
			}
		}
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}