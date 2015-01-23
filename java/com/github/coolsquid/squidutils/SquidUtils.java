package com.github.coolsquid.squidutils;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidlib.exception.InvalidConfigValueException;
import com.github.coolsquid.squidlib.util.Utils;
import com.github.coolsquid.squidutils.compat.AppleCoreCompat;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.creativetab.CreativeTabs;
import com.github.coolsquid.squidutils.handlers.AchievementHandler;
import com.github.coolsquid.squidutils.handlers.AnvilHandler;
import com.github.coolsquid.squidutils.handlers.BonemealHandler;
import com.github.coolsquid.squidutils.handlers.BottleHandler;
import com.github.coolsquid.squidutils.handlers.CommandHandler;
import com.github.coolsquid.squidutils.handlers.DebugHandler;
import com.github.coolsquid.squidutils.handlers.DifficultyHandler;
import com.github.coolsquid.squidutils.handlers.EventLogger;
import com.github.coolsquid.squidutils.handlers.RecipeHandler;
import com.github.coolsquid.squidutils.handlers.RegistrySearcher;
import com.github.coolsquid.squidutils.handlers.RenderDistanceHandler;
import com.github.coolsquid.squidutils.handlers.StackSizeHandler;
import com.github.coolsquid.squidutils.handlers.TNTHandler;
import com.github.coolsquid.squidutils.handlers.TeleportationHandler;
import com.github.coolsquid.squidutils.handlers.ToolHandler;
import com.github.coolsquid.squidutils.handlers.VillagerHandler;
import com.github.coolsquid.squidutils.handlers.WitherHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.util.CrashReportInterceptor;
import com.github.coolsquid.squidutils.util.Data;
import com.github.coolsquid.squidutils.util.ModLister;
import com.github.coolsquid.squidutils.util.PackIntegrityChecker;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * @since 1.0.0
 * 
 */

@Mod(modid = Data.modid, name = Data.name, version = Data.version, dependencies = Data.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	/**
	 * Preinit. Loads the config, clears Vanilla recipes (if toggled), and does environment checks.
	 * @param event
	 */
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing...");
		
		FMLCommonHandler.instance().registerCrashCallable(new CrashReportInterceptor());
		
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		
		if (ConfigHandler.modList.length != 0) {
			PackIntegrityChecker.check();
		}
		
		if (ConfigHandler.clearRecipes == 1) {
			CraftingManager.getInstance().getRecipeList().clear();
		}
		
		LogHelper.info("Preinitialization finished.");
	}
	
	/**
	 * Init. Activates modules.
	 * @param event
	 */
	
	@EventHandler
	private void init(FMLInitializationEvent event) {
		LogHelper.info("Initializing...");
		
		if (Utils.developmentEnvironment) {
			LogHelper.info("Running in a dev environment.");
			RecipeHandler.removeRecipes();
			ConfigHandler.debug = true;
		}
		
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && Utils.isClient()) {
			MinecraftForge.EVENT_BUS.register(new DifficultyHandler());
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("ConfigHandler.forceDifficulty");
		}
		if (ConfigHandler.noTNT) {
			MinecraftForge.EVENT_BUS.register(new TNTHandler());
		}
		if (ConfigHandler.noAchievements) {
			MinecraftForge.EVENT_BUS.register(new AchievementHandler());
		}
		if (ConfigHandler.noWitherBoss) {
			MinecraftForge.EVENT_BUS.register(new WitherHandler());
		}
		if (ConfigHandler.chainRecipes) {
			RecipeHandler.chainRecipes();
		}
		if (ConfigHandler.noDebug && Utils.isClient()) {
			MinecraftForge.EVENT_BUS.register(new DebugHandler());
		}
		if (ConfigHandler.maxRenderDistance != 16 && Utils.isClient()) {
			MinecraftForge.EVENT_BUS.register(new RenderDistanceHandler());
		}
		if (ConfigHandler.villagerProtection) {
			MinecraftForge.EVENT_BUS.register(new VillagerHandler());
		}
		if (ConfigHandler.tabVanilla) {
			CreativeTabs.preInit();
		}
		if (ConfigHandler.logStuff) {
			MinecraftForge.EVENT_BUS.register(new EventLogger());
		}
		if (ConfigHandler.disableAnvil) {
			MinecraftForge.EVENT_BUS.register(new AnvilHandler());
		}
		if (ConfigHandler.commandsToDisable.length != 0) {
			CommandHandler.init();
			MinecraftForge.EVENT_BUS.register(new CommandHandler());
		}
		if (ConfigHandler.disableTeleportation) {
			MinecraftForge.EVENT_BUS.register(new TeleportationHandler());
		}
		if (ConfigHandler.disableBonemeal) {
			MinecraftForge.EVENT_BUS.register(new BonemealHandler());
		}
		if (ConfigHandler.disableHoes) {
			MinecraftForge.EVENT_BUS.register(new ToolHandler());
		}
		if (ConfigHandler.disableBottleFluidInteraction) {
			MinecraftForge.EVENT_BUS.register(new BottleHandler());
		}
		if (ConfigHandler.generateModList != 0) {
			ModLister.init();
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		
		NBTTagCompound nbttag = new NBTTagCompound();
		nbttag.setString("curseProjectName", "226025-squidutils");
		nbttag.setString("curseFilenameParser", Data.modid + "-[].jar");
		FMLInterModComms.sendRuntimeMessage(Data.modid, "VersionChecker", "addCurseCheck", nbttag);
		
		LogHelper.info("Initialization finished.");
	}
	
	/**
	 * My postinit. Used for compatibility and universal changes.
	 * @param event
	 */
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing...");
		if (ConfigHandler.stackSizeDivider != 0 || ConfigHandler.durabilityDivider != 1 || ConfigHandler.infiniteDurability || ConfigHandler.allBlocksUnbreakable || ConfigHandler.hardnessMultiplier > 1) {
			RegistrySearcher.start();
		}
		if (ConfigHandler.clearRecipes == 2) {
			if (!Loader.isModLoaded("DragonAPI"))
				CraftingManager.getInstance().getRecipeList().clear();
		}
		if (ConfigHandler.potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
		RecipeHandler.removeRecipes();
		
		LogHelper.info("Postinitialization finished.");
	}
}