package com.github.coolsquid.SquidUtils.Handlers.Config;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
import com.github.coolsquid.SquidUtils.Handlers.RecipeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DebugHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DifficultyHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RenderDistanceHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.StackSizeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.TNTHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.VillagerHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.WitherHandler;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigHandler {

	private static Configuration config;
	
	public static void createConfig(File configFile)
	{
		if (config == null) {
			config = new Configuration(configFile);
			readConfig();
		}
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	
	private static String forceDifficulty = "FALSE";
	private static boolean NoTNT = false;
	private static boolean NoAchievements = false;
	private static boolean NoWitherBoss = false;
	private static int PotionStacks = 1;
	private static boolean ChainRecipes = false;
	private static boolean NoDebug = false;
	private static int PearlStack = 16;
	private static int MaxRenderDistance = 16;
	private static int MFR = 20;
	private static boolean OreDictComplain = true;
	private static boolean TNTDropItems = true;
	private static boolean DebugLogging = false;
	private static boolean UnHurtableVillager = false;
	
	private static void readConfig() {
		
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
		TNTDropItems = config.getBoolean("TNTDropItems", CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		DebugLogging = config.getBoolean("debugLogging", CATEGORY_GENERAL, false, "Enables debugging to the log.");
		UnHurtableVillager = config.getBoolean("unHurtableVillager", CATEGORY_GENERAL, false, "Makes villagers unhurtable.");
		
		if (config.hasChanged()) {
			config.save();
		}
		
		loadModules();
	}
	
	private static void loadModules() {
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE")) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			LogHandler.error("Error in the config. ForceDifficulty has a wrong value.");
		}
		if (ConfigHandler.NoTNT) {
			MinecraftForge.EVENT_BUS.register((Object)new TNTHandler());
		}
		if (ConfigHandler.NoAchievements) {
			MinecraftForge.EVENT_BUS.register((Object)new AchievementHandler());
		}
		if (ConfigHandler.NoWitherBoss) {
			MinecraftForge.EVENT_BUS.register((Object)new WitherHandler());
		}
		if (ConfigHandler.PotionStacks > 1 || ConfigHandler.PearlStack > 1) {
			StackSizeHandler.PreInit(ConfigHandler.PotionStacks, ConfigHandler.PearlStack);
		}
		if (ConfigHandler.ChainRecipes) {
			RecipeHandler.ChainRecipes();
		}
		if (ConfigHandler.NoDebug) {
			MinecraftForge.EVENT_BUS.register((Object)new DebugHandler());
		}
		if (ConfigHandler.MaxRenderDistance != 16) {
			MinecraftForge.EVENT_BUS.register((Object)new RenderDistanceHandler());
		}
		if (ConfigHandler.UnHurtableVillager) {
			MinecraftForge.EVENT_BUS.register((Object)new VillagerHandler());
		}
		LogConfig();
	}
	
	public static String getForceDifficulty() {
		return forceDifficulty;
	}
	
	public static boolean getNoTNT() {
		return NoTNT;
	}
	
	public static boolean getNoAchievements() {
		return NoAchievements;
	}
	
	public static boolean getNoWitherBoss() {
		return NoWitherBoss;
	}
	
	public static int getPotionStacks() {
		return PotionStacks;
	}
	
	public static boolean getChainRecipes() {
		return ChainRecipes;
	}
	
	public static boolean getNoDebug() {
		return NoDebug;
	}
	
	public static int getPearlStack() {
		return PearlStack;
	}
	
	public static int getMaxRenderDistance() {
		return MaxRenderDistance;
	}
	
	public static int getMFR() {
		return MFR;
	}
	
	public static boolean getOreDictComplain() {
		return OreDictComplain;
	}
	
	public static boolean getTNTDropItems() {
		return TNTDropItems;
	}
	
	public static boolean getDebugLogging() {
		return DebugLogging;
	}
	
	public static boolean getUnHurtableVillager() {
		return UnHurtableVillager;
	}
		
	private static void LogConfig() {
		LogHandler.debug("ConfigHandler.getForceDifficulty() = " + getForceDifficulty());
		LogHandler.debug("ConfigHandler.getNoTNT() = " + getNoTNT());
		LogHandler.debug("ConfigHandler.getNoAchievements() = " + getNoAchievements());
		LogHandler.debug("ConfigHandler.getNoWitherBoss() = " + getNoWitherBoss());
		LogHandler.debug("ConfigHandler.getPotionStacks() = " + getPotionStacks());
		LogHandler.debug("ConfigHandler.getNoDebug() = " + getNoDebug());
		LogHandler.debug("ConfigHandler.getPearlStack() = " + getPearlStack());
		LogHandler.debug("ConfigHandler.getMaxRenderDistance() = " + getMaxRenderDistance());
		LogHandler.debug("ConfigHandler.getMFR() = " + getMFR());
		LogHandler.debug("ConfigHandler.getOreDictComplain() = " + getOreDictComplain());
		LogHandler.debug("ConfigHandler.getTNTDropItems() = " + getTNTDropItems());
		LogHandler.debug("ConfigHandler.UnHurtableVillager = " + UnHurtableVillager);
	}
}