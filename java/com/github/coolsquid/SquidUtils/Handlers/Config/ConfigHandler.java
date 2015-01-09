package com.github.coolsquid.SquidUtils.Handlers.Config;

import java.io.File;

import javax.swing.JOptionPane;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.coolsquid.SquidUtils.CreativeTabs.VanillaTab;
import com.github.coolsquid.SquidUtils.Handlers.RunEventLogger;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
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

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigHandler implements Runnable {
	
	public static File configFile;
	
	@Override
	public void run() {
		preInit();
	}
	
	private static Configuration config;
	
	public static boolean eic(String s, String s2) {
		return s.equalsIgnoreCase(s2);
	}
	
	public static void preInit() {
		createConfig();
		initCategories();
		readConfig();
		loadModules();
	}
	
	private static void createConfig() {
		if (config == null)
			config = new Configuration(configFile);
	}
	
	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_COMPAT = "Compatibility";
	private static final String CATEGORY_UNHURTABLE = "Unhurtable mobs";
	private static final String CATEGORY_PROPERTIES = "Block and item properties";
	private static final String CATEGORY_GAMESETTINGS = "Force game options";
	private static final String CATEGORY_CREATIVETABS = "Creative tabs";
	
	public static String forceDifficulty = "FALSE";
	public static boolean noTNT = false;
	public static boolean noAchievements = false;
	public static boolean noWitherBoss = false;
	public static int potionStacks = 1;
	public static boolean chainRecipes = false;
	public static boolean noDebug = false;
	public static int pearlStack = 16;
	public static int maxRenderDistance = 16;
	public static int mfr = 20;
	public static boolean tntDropItems = true;
	public static boolean villagerProtection = false;
	public static boolean logStuff = false;
	public static int stackSizeDivider = 0;
	public static boolean allBlocksUnbreakable = false;
	public static int durabilityDivider = 1;
	public static boolean clearVanillaRecipes = false;
	public static String password = "";
	public static boolean tabVanilla = true;
	public static boolean infiniteDurability = false;
	
	private static void initCategories() {
		config.setCategoryComment(CATEGORY_GENERAL, "General options.");
		config.setCategoryComment(CATEGORY_COMPAT, "Compatibility options.");
		config.setCategoryComment(CATEGORY_UNHURTABLE, "Mob options.");
		config.setCategoryComment(CATEGORY_PROPERTIES, "Configure block and item properties.");
		config.setCategoryComment(CATEGORY_GAMESETTINGS, "Force game options.");
		config.setCategoryComment(CATEGORY_CREATIVETABS, "Disable or enable creative tabs.");
	}
	
	private static void readConfig() {
		
		forceDifficulty = config.getString("forceDifficulty", CATEGORY_GAMESETTINGS, "FALSE", "Forces the specified difficulty. Allows for HARD, noRMAL, EASY, PEACEFUL or FALSE. Set to FALSE to disable.");
		noTNT = config.getBoolean("noTNT", CATEGORY_GENERAL, false, "Stops TNT from exploding.");
		noAchievements = config.getBoolean("noAchievements", CATEGORY_GENERAL, false, "Disables achievements.");
		noWitherBoss = config.getBoolean("noWitherBoss", CATEGORY_GENERAL, false, "Disables the witherboss.");
		potionStacks = config.getInt("maxPotionStackSize", CATEGORY_PROPERTIES, 1, 1, 64, "Sets the max stacksize for potions.");
		chainRecipes = config.getBoolean("chainRecipes", CATEGORY_GENERAL, false, "Makes recipes for all pieces of chain armor.");
		noDebug = config.getBoolean("noDebug", CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		pearlStack = config.getInt("maxEnderPearlStackSize", CATEGORY_PROPERTIES, 16, 1, 64, "Sets the max stacksize for enderpearls.");
		maxRenderDistance = config.getInt("maxRenderDistance", CATEGORY_GAMESETTINGS, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		mfr = config.getInt("mfr", CATEGORY_COMPAT, 20, 0, 50, "Amount of lines...");
		tntDropItems = config.getBoolean("tntDropItems", CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		villagerProtection = config.getBoolean("villagerProtection", CATEGORY_UNHURTABLE, false, "Makes villagers unhurtable.");
		logStuff = config.getBoolean("logStuff", CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		stackSizeDivider = config.getInt("stackSizeDivider", CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		allBlocksUnbreakable = config.getBoolean("allBlocksUnbreakable", CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		durabilityDivider = config.getInt("durabilityDivider", CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		clearVanillaRecipes = config.getBoolean("clearVanillaRecipes", CATEGORY_GENERAL, false, "Clears all Vanilla recipes.");
		infiniteDurability = config.getBoolean("infiniteDurability", CATEGORY_PROPERTIES, false, "Makes all items have infinite durability. Overrides \"durabilityDivider\".");
		password = config.getString("password", CATEGORY_GENERAL, "", "Sets a password required to launch Minecraft.");
		tabVanilla = config.getBoolean("tabVanilla", CATEGORY_CREATIVETABS, true, "Enables the extra Vanilla stuff creative tab.");
		
		if (config.hasChanged()) {
			config.save();
		}
	}
	
	private static void loadModules() {
		if (!(ConfigHandler.password.isEmpty())) {
			try {
				String p = JOptionPane.showInputDialog("password:");
				if (!p.equals(ConfigHandler.password)) {
					throw new DO_NOT_REPORT_EXCEPTION("Wrong password.");
				}
			}
			catch (NullPointerException e) {
				throw new DO_NOT_REPORT_EXCEPTION("Wrong password.");
			}
		}
		
		if (!forceDifficulty.equalsIgnoreCase("FALSE") && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!eic(forceDifficulty, "FALSE") && !eic(forceDifficulty, "PEACEFUL") && !eic(forceDifficulty, "EASY") && !eic(forceDifficulty, "NORMAL") && !eic(forceDifficulty, "HARD")) {
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
			new Thread(new RunEventLogger()).start();
		}
		if (clearVanillaRecipes) {
			CraftingManager.getInstance().getRecipeList().clear();
		}
		if (tabVanilla && Data.isClient()) {
			VanillaTab.preInit();
		}
	}
	
	public static void postInit() {
		if (stackSizeDivider != 0 || durabilityDivider != 1 || infiniteDurability || allBlocksUnbreakable) {
			RegistrySearcher.start();
		}
		
		if (potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
	}
}