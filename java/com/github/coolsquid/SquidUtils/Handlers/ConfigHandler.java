package com.github.coolsquid.SquidUtils.Handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	private static Configuration config;
	
	public static void load(File configFile)
	{

		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	
	public static String forceDifficulty = "FALSE";
	public static boolean NoTNT = false;
	public static boolean NoAchievements = false;
	public static boolean NoWitherBoss = false;
	public static int PotionStacks = 1;
	public static boolean ChainRecipes = false;
	public static boolean NoDebug = false;
	public static int PearlStack = 16;
	public static int MaxRenderDistance = 16;
	public static int MFR = 20;
	public static boolean OreDictComplain = true;
	
	private static void loadConfiguration() {
		
		config.addCustomCategoryComment(CATEGORY_GENERAL, "General options.");
		config.setCategoryRequiresMcRestart(CATEGORY_GENERAL, true);
		config.addCustomCategoryComment(CATEGORY_COMPAT, "Compatibility options.");
		config.setCategoryRequiresMcRestart(CATEGORY_COMPAT, true);
		
		forceDifficulty = config.getString("forceHard", CATEGORY_GENERAL, "FALSE", "Forces the specified difficulty. Allows for HARD, NORMAL, EASY, PEACEFUL or FALSE. Set to FALSE to disable.");
		NoTNT = config.getBoolean("noTNT", CATEGORY_GENERAL, false, "Stops TNT from exploding.");
		NoAchievements = config.getBoolean("noAchievements", CATEGORY_GENERAL, false, "Disables achievements.");
		NoWitherBoss = config.getBoolean("noWitherBoss", CATEGORY_GENERAL, false, "Disables the witherboss.");
		PotionStacks = config.getInt("maxPotionStackSize", CATEGORY_GENERAL, 1, 1, 64, "Sets the max stacksize for potions.");
		ChainRecipes = config.getBoolean("chainRecipes", CATEGORY_GENERAL, false, "Makes recipes for all pieces of chain armor.");
		NoDebug = config.getBoolean("noDebug", CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		PearlStack = config.getInt("maxEnderPearlStackSize", CATEGORY_GENERAL, 16, 1, 64, "Sets the max stacksize for enderpearls.");
		MaxRenderDistance = config.getInt("maxRenderDistance", CATEGORY_GENERAL, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		MFR = config.getInt("MFR", CATEGORY_COMPAT, 20, 0, 50, "Amount of lines...");
		OreDictComplain = config.getBoolean("oreDictComplaining", CATEGORY_COMPAT, true, "Should the mod complain about long entries?");
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}