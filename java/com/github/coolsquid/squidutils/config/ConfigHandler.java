package com.github.coolsquid.squidutils.config;

import java.io.File;

import javax.swing.JOptionPane;

import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.squidutils.util.exception.DO_NOT_REPORT_EXCEPTION;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

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
	
	/*
	 * Hardcoded options. Modify them as you want to.
	 */
	
	public static boolean debug = false;
	
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
	
	public static boolean noTNT = false;
	
	/**
	 * Disables achievements.
	 */
	
	public static boolean noAchievements = false;
	
	/**
	 * Disables the wither.
	 */
	
	public static boolean noWitherBoss = false;
	
	/**
	 * Sets the max stack size for potions to the specified amount.
	 */
	
	public static int potionStacks = 1;
	
	/**
	 * Adds recipes for chain armor.
	 */
	
	public static boolean chainRecipes = false;
	
	/**
	 * Disables the debug screen.
	 */
	
	public static boolean noDebug = false;
	
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
	
	public static boolean villagerProtection = false;
	
	/**
	 * Logs all blocks broken and all entities killed.
	 */
	
	public static boolean logStuff = false;
	
	/**
	 * Divides all stack sizes by the specified amount.
	 */
	
	public static int stackSizeDivider = 0;
	
	/**
	 * Makes all blocks unbreakable.
	 */
	
	public static boolean allBlocksUnbreakable = false;
	
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
	
	public static boolean infiniteDurability = false;
	
	/**
	 * Multiplies all blocks hardness by the specified amount.
	 */
	
	public static float hardnessMultiplier = 1;
	
	/**
	 * Fixes a bug with glass bottles.
	 */
	
	public static boolean waterOnlyBottles = false;
	
	/**
	 * List of modids. All mods missing will be logged.
	 */
	
	public static String[] modList = new String[] {};

	/**
	 * Disables the Vanilla anvil.
	 */
	
	public static boolean disableAnvil = false;
	
	/**
	 * List of commands to disable.
	 */
	
	public static String[] commandsToDisable = new String[] {};
	
	/**
	 * Disables enderman and enderpearl teleportation.
	 */
	
	public static boolean disableTeleportation = false;

	/**
	 * Disables bonemeal.
	 */
	
	public static boolean disableBonemeal = false;
	
	/**
	 * Disables hoes.
	 */
	
	public static boolean disableHoes = false;
	
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
		tabVanilla = config.getBoolean("tabVanilla", CATEGORY_CREATIVETABS, true, "Enables the extra Vanilla stuff creative tab.");
		hardnessMultiplier = config.getFloat("hardnessMultiplier", CATEGORY_PROPERTIES, 1, 1, 100, "Multiplies all blocks hardness by the specified number. Set to 1.0 to disable.");
		//waterOnlyBottles = config.getBoolean("waterOnlyBottles", CATEGORY_GENERAL, false, "Makes water bottles only work with Vanilla water.");
		modList = config.getStringList("modList", CATEGORY_GENERAL, new String[] {}, "If any of the mods listed are missing, a warning will be printed to the log.");
		disableAnvil = config.getBoolean("disableAnvil", CATEGORY_GENERAL, false, "Disables the Vanilla anvil.");
		commandsToDisable = config.getStringList("commandsToDisable", CATEGORY_GENERAL, new String[] {}, "List of commands to disable.");
		disableTeleportation = config.getBoolean("disableTeleportation", CATEGORY_GENERAL, false, "Disables enderman and enderpearl teleportation.");
		disableBonemeal = config.getBoolean("disableBonemeal", CATEGORY_GENERAL, false, "Disables bonemeal.");
		disableHoes = config.getBoolean("disableHoes", CATEGORY_GENERAL, false, "Disables hoes.");
		
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