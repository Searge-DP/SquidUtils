/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils;

import java.io.File;
import java.util.Map;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidapi.Disableable;
import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.SquidAPIMod;
import com.github.coolsquid.squidapi.command.CommandDisable;
import com.github.coolsquid.squidapi.exception.InvalidConfigValueException;
import com.github.coolsquid.squidapi.reflection.ReflectionHelper;
import com.github.coolsquid.squidapi.util.ContentRemover;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.command.CommandSquidUtils;
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
import com.github.coolsquid.squidutils.scripting.ScriptHandler;
import com.github.coolsquid.squidutils.scripting.components.Components;
import com.github.coolsquid.squidutils.util.CrashReportInterceptor;
import com.github.coolsquid.squidutils.util.ModInfo;
import com.github.coolsquid.squidutils.util.ModLister;
import com.github.coolsquid.squidutils.util.PackIntegrityChecker;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.API;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, canBeDeactivated = true, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod implements Disableable {
	
	@Instance
	private static SquidUtils instance;
	private static ModContainer mod;
	@Deprecated
	private static API api;
	
	public SquidUtils() {
		super("Customization to the max!");
	}
	
	public static SquidUtils instance() {
		return instance;
	}
	
	public static API getAPI() {
		if (api == null) {
			api = ReflectionHelper.in("com.github.coolsquid.squidutils.api").getAnnotation(API.class);
		}
		return api;
	}
	
	public static ModContainer getMod() {
		return mod;
	}
	
	public static final Map<String, Object> handlers = Maps.newHashMap();
	public static final Map<String, Object> handlers2 = Maps.newHashMap();

	public static void registerHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
		handlers.put(object.getClass().getSimpleName(), object);
	}
	
	public static void registerHandler2(Object object) {
		FMLCommonHandler.instance().bus().register(object);
		handlers2.put(object.getClass().getSimpleName(), object);
	}
	
	public static boolean isDisabled;
	
	/**
	 * Preinit. Loads the config, clears Vanilla recipes (if toggled).
	 * @param event
	 */
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing.");
		
		mod = Loader.instance().activeModContainer();
		
		CommandDisable.disableables.put("SquidUtils", this);

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
		LogHelper.info("Initializing.");
		Components.init();
		if (Utils.developmentEnvironment()) {
			LogHelper.info("Running in new TNTHandler() dev environment.");
			ConfigHandler.debug = true;
		}
		
		ScriptingAPI.addTrigger("achievement", new AchievementHandler());
		ScriptingAPI.addTrigger("command", new CommandHandler());
		ScriptingAPI.addTrigger("teleport", new TeleportationHandler());
		ScriptingAPI.addTrigger("craft", new CraftingHandler());
		ScriptingAPI.addTrigger("smelt", new SmeltingHandler());
		ScriptingAPI.addTrigger("hurt", new DamageHandler());
		ScriptingAPI.addTrigger("heal", new HealingHandler());
		ScriptingAPI.addTrigger("toss", new TossHandler());
		ScriptingAPI.addTrigger("entityjoin", new EntityJoinHandler());
		ScriptingAPI.addTrigger("explosion", new ExplosionHandler());
		ScriptingAPI.addTrigger("interact", new InteractionHandler());
		ScriptingAPI.addTrigger("chat", new ServerChatHandler());
		
		try {
			ScriptHandler.init();
		} catch (Exception e) {
			e.printStackTrace();
			SquidAPI.logger.log(e);
			SquidAPI.messages.add(Utils.newString(e.getClass().getName(), ". See SquidAPI.log for more information."));
		}

		if (Utils.isClient()) {
			registerHandler(new DifficultyHandler());
			if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE")) {
				DifficultyHandler.difficulty = ConfigHandler.forceDifficulty;
			}
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("ConfigHandler.forceDifficulty");
		}
		if (ConfigHandler.noTNT) {
			registerHandler(new TNTHandler());
		}
		if (ConfigHandler.noAchievements || ScriptHandler.onAchievement) {
			registerHandler(new AchievementHandler());
		}
		if (ConfigHandler.noWitherBoss) {
			registerHandler(new WitherHandler());
		}
		if (ConfigHandler.chainRecipes) {
			ModRecipes.chain();
		}
		if (ConfigHandler.noDebug && Utils.isClient()) {
			registerHandler(new DebugHandler());
		}
		if (ConfigHandler.maxRenderDistance != 16 && Utils.isClient()) {
			registerHandler(new RenderDistanceHandler());
		}
		if (ConfigHandler.villagerProtection) {
			registerHandler(new VillagerHandler());
		}
		if (ConfigHandler.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ConfigHandler.logStuff) {
			registerHandler(new EventLogger());
		}
		if (ConfigHandler.disableAnvil) {
			registerHandler(new AnvilHandler());
		}
		if (!CommandHandler.commandsToDisable.isEmpty() || ScriptHandler.onCommand) {
			registerHandler(new CommandHandler());
		}
		if (ConfigHandler.disableTeleportation || ScriptHandler.onTeleport) {
			registerHandler(new TeleportationHandler());
		}
		if (ConfigHandler.disableBonemeal) {
			registerHandler(new BonemealHandler());
		}
		if (ConfigHandler.disableHoes) {
			registerHandler(new ToolHandler());
		}
		if (ConfigHandler.disableBottleFluidInteraction) {
			registerHandler(new BottleHandler());
		}
		if (ConfigHandler.generateModList != 0) {
			ModLister.init();
		}
		if (ConfigHandler.walkSpeed != 0.1F || ConfigHandler.flySpeed != 0.05F) {
			registerHandler(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.onCraft) {
			registerHandler2(new CraftingHandler());
		}
		if (ScriptHandler.onSmelt) {
			registerHandler2(new SmeltingHandler());
		}
		if (ScriptHandler.onHurt) {
			registerHandler(new DamageHandler());
		}
		if (ScriptHandler.onHeal) {
			registerHandler(new HealingHandler());
		}
		if (ScriptHandler.onToss) {
			registerHandler(new TossHandler());
		}
		if (ScriptHandler.onEntityJoin) {
			registerHandler(new EntityJoinHandler());
		}
		if (ConfigHandler.explosionSizeMultiplier != 1) {
			registerHandler(new ExplosionHandler());
		}
		if (ScriptHandler.onInteraction) {
			registerHandler(new InteractionHandler());
		}
		if (ScriptHandler.onChat) {
			registerHandler(new ServerChatHandler());
		}
		if (ScriptHandler.permissions) {
			PermissionHelper.init();
			registerHandler(new PermissionHelper());
		}
		if (ConfigHandler.worldSize > 0) {
			registerHandler(new LivingUpdateHandler());
		}
		if (ConfigHandler.explodeTNTMinecartsOnCollide) {
			registerHandler(new MinecartCollisionHandler());
		}
		SquidAPI.commands.add(new CommandSquidUtils());
		
		Utils.runVersionCheckerCompat("226025");
		
		LogHelper.info("Initialization finished.");
	}
	
	/**
	 * My postinit. Used for compatibility and universal changes.
	 * @param event
	 */
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing.");
		
		RegistrySearcher.start();
		
		if (ConfigHandler.potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
		
		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			registerHandler(new DropHandler());
		}
		
		LogHelper.info("Postinitialization finished.");
	}
	
	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (ConfigHandler.clearRecipes == 2) {
			if (!ContentRemover.isBlacklistedModLoaded()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
		}
	}
	
	@Override
	public boolean disable() {
		for (Object object: handlers.values()) {
			MinecraftForge.EVENT_BUS.unregister(object);
		}
		for (Object object: handlers2.values()) {
			FMLCommonHandler.instance().bus().unregister(object);
		}
		isDisabled = true;
		mod.setEnabledState(false);
		LogHelper.info("SquidUtils has been disabled.");
		return true;
	}

	@Override
	public boolean enable() {
		for (Object object: handlers.values()) {
			MinecraftForge.EVENT_BUS.register(object);
		}
		for (Object object: handlers2.values()) {
			FMLCommonHandler.instance().bus().register(object);
		}
		isDisabled = false;
		mod.setEnabledState(true);
		LogHelper.info("SquidUtils has been enabled.");
		return true;
	}
}