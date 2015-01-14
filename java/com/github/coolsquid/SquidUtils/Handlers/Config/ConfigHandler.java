package com.github.coolsquid.SquidUtils.Handlers.Config;

import java.io.File;

import javax.swing.JOptionPane;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.SquidUtils.CreativeTabs.CreativeTabs;
import com.github.coolsquid.SquidUtils.Handlers.EventLogger;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.BottleHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DebugHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DifficultyHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RecipeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RegistrySearcher;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RenderDistanceHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.StackSizeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.TNTHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.VillagerHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.WitherHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;
import com.github.coolsquid.SquidUtils.Utils.Exception.DO_NOT_REPORT_EXCEPTION;
import com.github.coolsquid.SquidUtils.Utils.Exception.InvalidConfigValueException;

import cpw.mods.fml.common.Loader;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigHandler {
	
	private static File configFile;
	private static Configuration config;
	
	public static final void preInit(File file) {
		configFile = file;
		createConfig();
		initCategories();
		readConfig();
		loadModules();
	}
	
	private static final void createConfig() {
		if (config == null)
			config = new Configuration(configFile);
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	private static final String CATEGORY_UNHURTABLE = "Unhurtable mobs";
	private static final String CATEGORY_PROPERTIES = "Block and item properties";
	private static final String CATEGORY_GAMESETTINGS = "Force game options";
	private static final String CATEGORY_CREATIVETABS = "Creative tabs";
	
	public static boolean debug = false;
	
	public static String forceDifficulty = "FALSE";
	public static boolean noTNT = false;
	public static boolean noAchievements = false;
	public static boolean noWitherBoss = false;
	public static int potionStacks = 1;
	public static boolean chainRecipes = false;
	public static boolean noDebug = false;
	public static int pearlStack = 16;
	public static int maxRenderDistance = 16;
	public static boolean tntDropItems = true;
	public static boolean villagerProtection = false;
	public static boolean logStuff = false;
	public static int stackSizeDivider = 0;
	public static boolean allBlocksUnbreakable = false;
	public static int durabilityDivider = 1;
	public static int clearRecipes = 0;
	public static boolean tabVanilla = true;
	public static boolean infiniteDurability = false;
	public static float hardnessMultiplier = 1;
	public static boolean nerfBottles = false;
	
	private static void initCategories() {
		config.setCategoryComment(CATEGORY_GENERAL, "General options.");
		config.setCategoryComment(CATEGORY_COMPAT, "Compatibility options.");
		config.setCategoryComment(CATEGORY_UNHURTABLE, "Mob options.");
		config.setCategoryComment(CATEGORY_PROPERTIES, "Configure block and item properties.");
		config.setCategoryComment(CATEGORY_GAMESETTINGS, "Force game options.");
		config.setCategoryComment(CATEGORY_CREATIVETABS, "Disable or enable creative tabs.");
	}
	
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
		villagerProtection = config.getBoolean("villagerProtection", CATEGORY_UNHURTABLE, false, "Makes villagers unhurtable.");
		logStuff = config.getBoolean("logStuff", CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		stackSizeDivider = config.getInt("stackSizeDivider", CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		allBlocksUnbreakable = config.getBoolean("allBlocksUnbreakable", CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		durabilityDivider = config.getInt("durabilityDivider", CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		clearRecipes = config.getInt("clearRecipes", CATEGORY_GENERAL, 0, 0, 2, "Clears Vanilla recipes if 1, clears all recipes if 2. Set to 0 to disable. Will not work if any of Reika's mods are loaded.");
		infiniteDurability = config.getBoolean("infiniteDurability", CATEGORY_PROPERTIES, false, "Makes all items have infinite durability. Overrides \"durabilityDivider\".");
		tabVanilla = config.getBoolean("tabVanilla", CATEGORY_CREATIVETABS, true, "Enables the extra Vanilla stuff creative tab.");
		hardnessMultiplier = config.getFloat("hardnessMultiplier", CATEGORY_PROPERTIES, 1, 1, 100, "Multiplies all blocks hardness by the specified number. Set to 1.0 to disable.");
		nerfBottles = config.getBoolean("nerfBottles", CATEGORY_GENERAL, false, "Nerfs bottles by removing the water source block after it has been used.");
		
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
	
	private static final void loadModules() {		
		if (!forceDifficulty.equalsIgnoreCase("FALSE") && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!forceDifficulty.equalsIgnoreCase("FALSE") && !forceDifficulty.equalsIgnoreCase("PEACEFUL") && !forceDifficulty.equalsIgnoreCase("EASY") && !forceDifficulty.equalsIgnoreCase("NORMAL") && !forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("forceDifficulty");
		}
		if (noTNT) {
			MinecraftForge.EVENT_BUS.register((Object)new TNTHandler());
		}
		if (noAchievements) {
			MinecraftForge.EVENT_BUS.register((Object)new AchievementHandler());
		}
		if (noWitherBoss) {
			MinecraftForge.EVENT_BUS.register((Object)new WitherHandler());
		}
		if (chainRecipes) {
			RecipeHandler.chainRecipes();
		}
		if (noDebug && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new DebugHandler());
		}
		if (maxRenderDistance != 16 && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new RenderDistanceHandler());
		}
		if (villagerProtection) {
			MinecraftForge.EVENT_BUS.register((Object)new VillagerHandler());
		}
		if (logStuff) {
			MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
		}
		if (clearRecipes == 1) {
			CraftingManager.getInstance().getRecipeList().clear();
		}
		if (tabVanilla && Data.isClient()) {
			CreativeTabs.preInit();
		}
		if (nerfBottles) {
			MinecraftForge.EVENT_BUS.register(new BottleHandler());
		}
	}
	
	public static final void postInit() {
		if (stackSizeDivider != 0 || durabilityDivider != 1 || infiniteDurability || allBlocksUnbreakable || hardnessMultiplier > 1) {
			RegistrySearcher.start();
		}
		if (clearRecipes == 2) {
			if (!Loader.isModLoaded("DragonAPI"))
				CraftingManager.getInstance().getRecipeList().clear();
		}
		if (potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
	}
}