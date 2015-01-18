package com.github.coolsquid.squidutils;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.creativetabs.CreativeTabs;
import com.github.coolsquid.squidutils.handlers.AchievementHandler;
import com.github.coolsquid.squidutils.handlers.AnvilHandler;
import com.github.coolsquid.squidutils.handlers.BonemealHandler;
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
import com.github.coolsquid.squidutils.util.CommonHandler;
import com.github.coolsquid.squidutils.util.Data;
import com.github.coolsquid.squidutils.util.PackIntegrityChecker;
import com.github.coolsquid.squidutils.util.exception.InvalidConfigValueException;
import com.github.coolsquid.squidutils.util.logging.LogHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
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
		
		CommonHandler.init();
		
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		
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
		
		PackIntegrityChecker.check();
		
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new DifficultyHandler());
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("ConfigHandler.forceDifficulty");
		}
		if (ConfigHandler.noTNT) {
			MinecraftForge.EVENT_BUS.register((Object)new TNTHandler());
		}
		if (ConfigHandler.noAchievements) {
			MinecraftForge.EVENT_BUS.register((Object)new AchievementHandler());
		}
		if (ConfigHandler.noWitherBoss) {
			MinecraftForge.EVENT_BUS.register((Object)new WitherHandler());
		}
		if (ConfigHandler.chainRecipes) {
			RecipeHandler.chainRecipes();
		}
		if (ConfigHandler.noDebug && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new DebugHandler());
		}
		if (ConfigHandler.maxRenderDistance != 16 && Data.isClient()) {
			MinecraftForge.EVENT_BUS.register((Object)new RenderDistanceHandler());
		}
		if (ConfigHandler.villagerProtection) {
			MinecraftForge.EVENT_BUS.register((Object)new VillagerHandler());
		}
		if (ConfigHandler.logStuff) {
			MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
		}
		if (ConfigHandler.tabVanilla && Data.isClient()) {
			CreativeTabs.preInit();
		}
		/*
		if (ConfigHandler.waterOnlyBottles) {
			TODO
		}
		*/
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