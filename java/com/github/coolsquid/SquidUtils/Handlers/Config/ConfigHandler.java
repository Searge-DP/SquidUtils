package com.github.coolsquid.SquidUtils.Handlers.Config;

import java.io.File;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.SquidUtils.Handlers.EventLogger;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.BlockSearcher;
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
import com.github.coolsquid.SquidUtils.Utils.Exception.InvalidConfigValueException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigHandler {
	
	private static Configuration config;
		
	public static boolean eic(String s, String s2) {
		return s.equalsIgnoreCase(s2);
	}
	
	public static void preInit(File configFile) {
		if (config == null) {
			createConfig(configFile);
		}
		initCategories();
		readConfig();
		loadModules();
		DebugConfig();
	}
	
	private static void createConfig(File configFile) {
		config = new Configuration(configFile);
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	private static final String CATEGORY_UNHURTABLE = "Unhurtable mobs";
	private static final String CATEGORY_PROPERTIES = "Block and item properties";
	private static final String CATEGORY_GAMESETTINGS = "Force game options";
	
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
	public static boolean TNTDropItems = true;
	public static boolean DebugLogging = false;
	public static boolean VillagerProtection = false;
	public static boolean LogStuff = false;
	public static int StackSizeDivider = 0;
	public static boolean AllBlocksUnbreakable = false;
	public static int DurabilityDivider = 1;
	public static boolean ClearVanillaRecipes = false;
	
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
		ClearVanillaRecipes = config.getBoolean("clearVanillaRecipes", CATEGORY_GENERAL, false, "Clears all Vanilla recipes.");
		
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	private static void loadModules() {
		if (!forceDifficulty.equalsIgnoreCase("FALSE") && FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!eic(forceDifficulty, "FALSE") && !eic(forceDifficulty, "PEACEFUL") && !eic(forceDifficulty, "EASY") && !eic(forceDifficulty, "NORMAL") && !eic(forceDifficulty, "HARD")) {
			throw new InvalidConfigValueException("forceDifficulty");
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
		if (NoDebug && FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			MinecraftForge.EVENT_BUS.register((Object)new DebugHandler());
		}
		if (MaxRenderDistance != 16 && FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			MinecraftForge.EVENT_BUS.register((Object)new RenderDistanceHandler());
		}
		if (VillagerProtection) {
			MinecraftForge.EVENT_BUS.register((Object)new VillagerHandler());
		}
		if (LogStuff) {
			MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
		}
		if (ClearVanillaRecipes) {
			CraftingManager.getInstance().getRecipeList().clear();
		}
	}
	
	private static void DebugConfig() {
		LogHelper.debug("ConfigHandler.getForceDifficulty() = " + forceDifficulty);
		LogHelper.debug("ConfigHandler.getNoTNT() = " + NoTNT);
		LogHelper.debug("ConfigHandler.getNoAchievements() = " + NoAchievements);
		LogHelper.debug("ConfigHandler.getNoWitherBoss() = " + NoWitherBoss);
		LogHelper.debug("ConfigHandler.getPotionStacks() = " + PotionStacks);
		LogHelper.debug("ConfigHandler.getNoDebug() = " + NoDebug);
		LogHelper.debug("ConfigHandler.getPearlStack() = " + PearlStack);
		LogHelper.debug("ConfigHandler.getMaxRenderDistance() = " + MaxRenderDistance);
		LogHelper.debug("ConfigHandler.getMFR() = " + MFR);
		LogHelper.debug("ConfigHandler.getOreDictComplain() = " + OreDictComplain);
		LogHelper.debug("ConfigHandler.getTNTDropItems() = " + TNTDropItems);
		LogHelper.debug("ConfigHandler.getVillagerProtection() = " + VillagerProtection);
		LogHelper.debug("ConfigHandler.getLogStuff() = " + LogStuff);
		LogHelper.debug("ConfigHandler.getAllBlocksUnbreakable = " + AllBlocksUnbreakable);
		LogHelper.debug("ConfigHandler.getDurabilityDivider = " + DurabilityDivider);
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