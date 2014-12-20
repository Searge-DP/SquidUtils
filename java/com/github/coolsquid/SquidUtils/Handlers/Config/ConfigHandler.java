package com.github.coolsquid.SquidUtils.Handlers.Config;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.SquidUtils.Exception.InvalidConfigValueException;
import com.github.coolsquid.SquidUtils.Handlers.EventLogger;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.BlockSearcher;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DamageHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DebugHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DifficultyHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.ItemSearcher;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RecipeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RenderDistanceHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.StackSizeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.TNTHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.VillagerHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.WitherHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigHandler {

	private static Configuration config;
	
	public static void preInit(File configFile) {
		if (config == null) {
			createConfig(configFile);
		}
		initCategories();
		readConfig();
		loadModules();
		DebugConfig();
	}
	
	private static void createConfig(File configFile)
	{
		config = new Configuration(configFile);
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	private static final String CATEGORY_UNHURTABLE = "Unhurtable mobs";
	private static final String CATEGORY_PROPERTIES = "Block and item properties";
	private static final String CATEGORY_GAMESETTINGS = "Force game options";
	
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
	private static boolean VillagerProtection = false;
	private static boolean LogStuff = false;
	private static int StackSizeDivider = 0;
	private static boolean AllBlocksUnbreakable = false;
	private static int DurabilityDivider = 1;
	private static boolean DamageNiceCreatures = false;
	
	private static void initCategories() {
		config.setCategoryComment(CATEGORY_GENERAL, "General options.");
		config.setCategoryComment(CATEGORY_COMPAT, "Compatibility options.");
		config.setCategoryComment(CATEGORY_UNHURTABLE, "Mob options.");
		config.setCategoryComment(CATEGORY_PROPERTIES, "Configure block and item properties.");
		config.setCategoryComment(CATEGORY_GAMESETTINGS, "Force game options.");
	}
	
	private static void readConfig() {
		
		forceDifficulty = config.getString("forceHard", CATEGORY_GAMESETTINGS, "FALSE", "Forces the specified difficulty. Allows for HARD, NORMAL, EASY, PEACEFUL or FALSE. Set to FALSE to disable.");
		NoTNT = config.getBoolean("noTNT", CATEGORY_GENERAL, false, "Stops TNT from exploding.");
		NoAchievements = config.getBoolean("noAchievements", CATEGORY_GENERAL, false, "Disables achievements.");
		NoWitherBoss = config.getBoolean("noWitherBoss", CATEGORY_GENERAL, false, "Disables the witherboss.");
		PotionStacks = config.getInt("maxPotionStackSize", CATEGORY_PROPERTIES, 1, 1, 64, "Sets the max stacksize for potions.");
		ChainRecipes = config.getBoolean("chainRecipes", CATEGORY_GENERAL, false, "Makes recipes for all pieces of chain armor.");
		NoDebug = config.getBoolean("noDebug", CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		PearlStack = config.getInt("maxEnderPearlStackSize", CATEGORY_PROPERTIES, 16, 1, 64, "Sets the max stacksize for enderpearls.");
		MaxRenderDistance = config.getInt("maxRenderDistance", CATEGORY_GAMESETTINGS, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		MFR = config.getInt("MFR", CATEGORY_COMPAT, 20, 0, 50, "Amount of lines...");
		OreDictComplain = config.getBoolean("oreDictComplaining", CATEGORY_COMPAT, true, "Should the mod complain about long entries?");
		TNTDropItems = config.getBoolean("TNTDropItems", CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		DebugLogging = config.getBoolean("debugLogging", CATEGORY_GENERAL, false, "Enables debugging to the log.");
		VillagerProtection = config.getBoolean("villagerProtection", CATEGORY_UNHURTABLE, false, "Makes villagers unhurtable.");
		LogStuff = config.getBoolean("logStuff", CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		StackSizeDivider = config.getInt("stackSizeDivider", CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		AllBlocksUnbreakable = config.getBoolean("allBlocksUnbreakable", CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		DurabilityDivider = config.getInt("durabilityDivider", CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		DamageNiceCreatures = config.getBoolean("damageNiceCreatures", CATEGORY_GENERAL, false, "Randomly damages players, and kills animals.");
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	private static void loadModules() {
		if (!forceDifficulty.equalsIgnoreCase("FALSE")) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!forceDifficulty.equalsIgnoreCase("FALSE") && !forceDifficulty.equalsIgnoreCase("PEACEFUL") && !forceDifficulty.equalsIgnoreCase("EASY") && !forceDifficulty.equalsIgnoreCase("NORMAL") && !forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("error at \"forceDifficulty\"");
		}
		if (NoTNT) {
			MinecraftForge.EVENT_BUS.register((Object)new TNTHandler());
		}
		if (NoAchievements) {
			MinecraftForge.EVENT_BUS.register((Object)new AchievementHandler());
		}
		if (NoWitherBoss) {
			MinecraftForge.EVENT_BUS.register((Object)new WitherHandler());
		}
		if (ChainRecipes) {
			RecipeHandler.ChainRecipes();
		}
		if (NoDebug) {
			MinecraftForge.EVENT_BUS.register((Object)new DebugHandler());
		}
		if (MaxRenderDistance != 16) {
			MinecraftForge.EVENT_BUS.register((Object)new RenderDistanceHandler());
		}
		if (VillagerProtection) {
			MinecraftForge.EVENT_BUS.register((Object)new VillagerHandler());
		}
		if (LogStuff) {
			MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
		}
		if (DamageNiceCreatures) {
			MinecraftForge.EVENT_BUS.register((Object)new DamageHandler());
		}
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
	
	public static boolean getVillagerProtection() {
		return VillagerProtection;
	}
	
	public static boolean getLogStuff() {
		return LogStuff;
	}
	
	public static int getMaxStackSize() {
		return StackSizeDivider;
	}
	
	public static boolean getAllBlocksUnbreakable() {
		return AllBlocksUnbreakable;
	}
	
	public static int getDurabilityDivider() {
		return DurabilityDivider;
	}
	
	public static boolean getDamageNiceCreatures() {
		return DamageNiceCreatures;
	}
	
	private static void DebugConfig() {
		LogHelper.debug("ConfigHandler.getForceDifficulty() = " + getForceDifficulty());
		LogHelper.debug("ConfigHandler.getNoTNT() = " + getNoTNT());
		LogHelper.debug("ConfigHandler.getNoAchievements() = " + getNoAchievements());
		LogHelper.debug("ConfigHandler.getNoWitherBoss() = " + getNoWitherBoss());
		LogHelper.debug("ConfigHandler.getPotionStacks() = " + getPotionStacks());
		LogHelper.debug("ConfigHandler.getNoDebug() = " + getNoDebug());
		LogHelper.debug("ConfigHandler.getPearlStack() = " + getPearlStack());
		LogHelper.debug("ConfigHandler.getMaxRenderDistance() = " + getMaxRenderDistance());
		LogHelper.debug("ConfigHandler.getMFR() = " + getMFR());
		LogHelper.debug("ConfigHandler.getOreDictComplain() = " + getOreDictComplain());
		LogHelper.debug("ConfigHandler.getTNTDropItems() = " + getTNTDropItems());
		LogHelper.debug("ConfigHandler.getVillagerProtection() = " + getVillagerProtection());
		LogHelper.debug("ConfigHandler.getLogStuff() = " + getLogStuff());
		LogHelper.debug("ConfigHandler.getAllBlocksUnbreakable = " + getAllBlocksUnbreakable());
		LogHelper.debug("ConfigHandler.getDurabilityDivider = " + getDurabilityDivider());
		LogHelper.debug("ConfigHandler.getDamageNiceCreatures = " + getDamageNiceCreatures());
	}
	
	public static void postInit() {
		if (StackSizeDivider != 0 || DurabilityDivider != 1) {
			ItemSearcher.search(StackSizeDivider, DurabilityDivider);
		}
		
		if (AllBlocksUnbreakable) {
			BlockSearcher.search();
		}
		
		if (PotionStacks > 1 || ConfigHandler.PearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.PotionStacks, ConfigHandler.PearlStack);
		}
	}
}