/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils;

import java.io.File;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidapi.SquidAPIMod;
import com.github.coolsquid.squidapi.exception.InvalidConfigValueException;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.compat.AppleCoreCompat;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.creativetab.ModCreativeTabs;
import com.github.coolsquid.squidutils.handlers.AchievementHandler;
import com.github.coolsquid.squidutils.handlers.AnvilHandler;
import com.github.coolsquid.squidutils.handlers.BonemealHandler;
import com.github.coolsquid.squidutils.handlers.BottleHandler;
import com.github.coolsquid.squidutils.handlers.CommandHandler;
import com.github.coolsquid.squidutils.handlers.CraftingHandler;
import com.github.coolsquid.squidutils.handlers.DamageHandler;
import com.github.coolsquid.squidutils.handlers.DebugHandler;
import com.github.coolsquid.squidutils.handlers.DifficultyHandler;
import com.github.coolsquid.squidutils.handlers.DropHandler;
import com.github.coolsquid.squidutils.handlers.EntityJoinHandler;
import com.github.coolsquid.squidutils.handlers.EventLogger;
import com.github.coolsquid.squidutils.handlers.ExplosionHandler;
import com.github.coolsquid.squidutils.handlers.HealingHandler;
import com.github.coolsquid.squidutils.handlers.InteractionHandler;
import com.github.coolsquid.squidutils.handlers.LivingUpdateHandler;
import com.github.coolsquid.squidutils.handlers.MinecartCollisionHandler;
import com.github.coolsquid.squidutils.handlers.RegistrySearcher;
import com.github.coolsquid.squidutils.handlers.RenderDistanceHandler;
import com.github.coolsquid.squidutils.handlers.ScriptHandler;
import com.github.coolsquid.squidutils.handlers.ServerChatHandler;
import com.github.coolsquid.squidutils.handlers.SmeltingHandler;
import com.github.coolsquid.squidutils.handlers.SpeedHandler;
import com.github.coolsquid.squidutils.handlers.StackSizeHandler;
import com.github.coolsquid.squidutils.handlers.TNTHandler;
import com.github.coolsquid.squidutils.handlers.TeleportationHandler;
import com.github.coolsquid.squidutils.handlers.ToolHandler;
import com.github.coolsquid.squidutils.handlers.TossHandler;
import com.github.coolsquid.squidutils.handlers.VillagerHandler;
import com.github.coolsquid.squidutils.handlers.WitherHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.helpers.PermissionHelper;
import com.github.coolsquid.squidutils.util.CrashReportInterceptor;
import com.github.coolsquid.squidutils.util.ModInfo;
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

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod {
	
	public SquidUtils() {
		super("Customization to the max!");
	}

	/**
	 * Preinit. Loads the config, clears Vanilla recipes (if toggled).
	 * @param event
	 */
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing...");
		
		new File("./config/SquidUtils").mkdirs();
		ConfigHandler.preInit(new File("./config/SquidUtils/SquidUtils.cfg"));
		
		if (ConfigHandler.modList.length != 0) {
			PackIntegrityChecker.check();
			FMLCommonHandler.instance().registerCrashCallable(new CrashReportInterceptor());
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
			ConfigHandler.debug = true;
		}
		
		ScriptHandler.init();

		if (Utils.isClient()) {
			MinecraftForge.EVENT_BUS.register(new DifficultyHandler());
			if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE")) {
				DifficultyHandler.difficulty = ConfigHandler.forceDifficulty;
			}
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("ConfigHandler.forceDifficulty");
		}
		if (ConfigHandler.noTNT) {
			MinecraftForge.EVENT_BUS.register(new TNTHandler());
		}
		if (ConfigHandler.noAchievements || ScriptHandler.onAchievement) {
			MinecraftForge.EVENT_BUS.register(new AchievementHandler());
		}
		if (ConfigHandler.noWitherBoss) {
			MinecraftForge.EVENT_BUS.register(new WitherHandler());
		}
		if (ConfigHandler.chainRecipes) {
			ModRecipes.chain();
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
			ModCreativeTabs.preInit();
		}
		if (ConfigHandler.logStuff) {
			MinecraftForge.EVENT_BUS.register(new EventLogger());
		}
		if (ConfigHandler.disableAnvil) {
			MinecraftForge.EVENT_BUS.register(new AnvilHandler());
		}
		if (!CommandHandler.commandsToDisable.isEmpty() || ScriptHandler.onCommand) {
			MinecraftForge.EVENT_BUS.register(new CommandHandler());
		}
		if (ConfigHandler.disableTeleportation || ScriptHandler.onTeleport) {
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
		if (ConfigHandler.walkSpeed != 0.1F || ConfigHandler.flySpeed != 0.05F) {
			MinecraftForge.EVENT_BUS.register(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.onCraft) {
			FMLCommonHandler.instance().bus().register(new CraftingHandler());
		}
		if (ScriptHandler.onSmelt) {
			FMLCommonHandler.instance().bus().register(new SmeltingHandler());
		}
		if (ScriptHandler.onHurt) {
			MinecraftForge.EVENT_BUS.register(new DamageHandler());
		}
		if (ScriptHandler.onHeal) {
			MinecraftForge.EVENT_BUS.register(new HealingHandler());
		}
		if (ScriptHandler.onToss) {
			MinecraftForge.EVENT_BUS.register(new TossHandler());
		}
		if (ScriptHandler.onEntityJoin) {
			MinecraftForge.EVENT_BUS.register(new EntityJoinHandler());
		}
		if (ConfigHandler.explosionSizeMultiplier != 1) {
			MinecraftForge.EVENT_BUS.register(new ExplosionHandler());
		}
		if (ScriptHandler.onInteraction) {
			MinecraftForge.EVENT_BUS.register(new InteractionHandler());
		}
		if (ScriptHandler.onChat) {
			MinecraftForge.EVENT_BUS.register(new ServerChatHandler());
		}
		if (ScriptHandler.permissions) {
			PermissionHelper.init();
			MinecraftForge.EVENT_BUS.register(new PermissionHelper());
		}
		if (ConfigHandler.worldSize > 0) {
			MinecraftForge.EVENT_BUS.register(new LivingUpdateHandler());
		}
		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			MinecraftForge.EVENT_BUS.register(new DropHandler());
		}
		if (ConfigHandler.explodeTNTMinecartsOnCollide) {
			MinecraftForge.EVENT_BUS.register(new MinecartCollisionHandler());
		}
		
		NBTTagCompound nbttag = new NBTTagCompound();
		nbttag.setString("curseProjectName", "226025-squidutils");
		nbttag.setString("curseFilenameParser", ModInfo.modid + "-[].jar");
		FMLInterModComms.sendRuntimeMessage(ModInfo.modid, "VersionChecker", "addCurseCheck", nbttag);
		
		LogHelper.info("Initialization finished.");
	}
	
	/**
	 * My postinit. Used for compatibility and universal changes.
	 * @param event
	 */
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing...");
		
		RegistrySearcher.start();
		if (ConfigHandler.clearRecipes == 2) {
			if (!Utils.doNotClearRecipes()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
		}
		if (ConfigHandler.potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
		
		LogHelper.info("Postinitialization finished.");
	}
}