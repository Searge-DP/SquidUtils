/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils;

import java.io.File;
import java.util.Set;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidapi.Disableable;
import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.SquidAPIMod;
import com.github.coolsquid.squidapi.command.CommandDisable;
import com.github.coolsquid.squidapi.exception.InvalidConfigValueException;
import com.github.coolsquid.squidapi.util.ContentRemover;
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
import com.google.common.collect.Sets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, canBeDeactivated = true, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod implements Disableable {
	
	public static ModContainer mod;
	
	public SquidUtils() {
		super("Customization to the max!");
	}
	
	public static final Object a = new TNTHandler();
	public static final Object b = new AchievementHandler();
	public static final Object c = new WitherHandler();
	public static final Object d = new DebugHandler();
	public static final Object e = new RenderDistanceHandler();
	public static final Object f = new VillagerHandler();
	public static final Object g = new EventLogger();
	public static final Object h = new AnvilHandler();
	public static final Object i = new CommandHandler();
	public static final Object j = new TeleportationHandler();
	public static final Object k = new BonemealHandler();
	public static final Object l = new ToolHandler();
	public static final Object m = new BottleHandler();
	public static final Object n = new SpeedHandler();
	public static final Object o = new CraftingHandler();
	public static final Object p = new SmeltingHandler();
	public static final Object q = new DamageHandler();
	public static final Object r = new HealingHandler();
	public static final Object s = new TossHandler();
	public static final Object t = new EntityJoinHandler();
	public static final Object u = new ExplosionHandler();
	public static final Object v = new InteractionHandler();
	public static final Object w = new ServerChatHandler();
	public static final Object x = new PermissionHelper();
	public static final Object y = new LivingUpdateHandler();
	public static final Object z = new MinecartCollisionHandler();
	
	public static final Set<Object> handlers = Sets.newHashSet();
	public static final Set<Object> handlers2 = Sets.newHashSet();

	public static void registerHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
		handlers.add(object);
	}
	
	public static void unregisterHandler(Object object) {
		MinecraftForge.EVENT_BUS.unregister(object);
		handlers.remove(object);
	}
	
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
		
		if (Utils.developmentEnvironment()) {
			LogHelper.info("Running in a dev environment.");
			ConfigHandler.debug = true;
		}
		
		try {
			ScriptHandler.init();
		} catch (Exception e) {
			e.printStackTrace();
			SquidAPI.logger.log(e.getStackTrace());
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
			registerHandler(a);
		}
		if (ConfigHandler.noAchievements || ScriptHandler.onAchievement) {
			registerHandler(b);
		}
		if (ConfigHandler.noWitherBoss) {
			registerHandler(c);
		}
		if (ConfigHandler.chainRecipes) {
			ModRecipes.chain();
		}
		if (ConfigHandler.noDebug && Utils.isClient()) {
			registerHandler(d);
		}
		if (ConfigHandler.maxRenderDistance != 16 && Utils.isClient()) {
			registerHandler(e);
		}
		if (ConfigHandler.villagerProtection) {
			registerHandler(f);
		}
		if (ConfigHandler.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ConfigHandler.logStuff) {
			registerHandler(g);
		}
		if (ConfigHandler.disableAnvil) {
			registerHandler(h);
		}
		if (!CommandHandler.commandsToDisable.isEmpty() || ScriptHandler.onCommand) {
			registerHandler(i);
		}
		if (ConfigHandler.disableTeleportation || ScriptHandler.onTeleport) {
			registerHandler(j);
		}
		if (ConfigHandler.disableBonemeal) {
			registerHandler(k);
		}
		if (ConfigHandler.disableHoes) {
			registerHandler(l);
		}
		if (ConfigHandler.disableBottleFluidInteraction) {
			registerHandler(m);
		}
		if (ConfigHandler.generateModList != 0) {
			ModLister.init();
		}
		if (ConfigHandler.walkSpeed != 0.1F || ConfigHandler.flySpeed != 0.05F) {
			registerHandler(n);
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.onCraft) {
			FMLCommonHandler.instance().bus().register(o);
			handlers2.add(o);
		}
		if (ScriptHandler.onSmelt) {
			FMLCommonHandler.instance().bus().register(p);
			handlers2.add(p);
		}
		if (ScriptHandler.onHurt) {
			registerHandler(q);
		}
		if (ScriptHandler.onHeal) {
			registerHandler(r);
		}
		if (ScriptHandler.onToss) {
			registerHandler(s);
		}
		if (ScriptHandler.onEntityJoin) {
			registerHandler(t);
		}
		if (ConfigHandler.explosionSizeMultiplier != 1) {
			registerHandler(u);
		}
		if (ScriptHandler.onInteraction) {
			registerHandler(v);
		}
		if (ScriptHandler.onChat) {
			registerHandler(w);
		}
		if (ScriptHandler.permissions) {
			PermissionHelper.init();
			registerHandler(x);
		}
		if (ConfigHandler.worldSize > 0) {
			registerHandler(y);
		}
		if (ConfigHandler.explodeTNTMinecartsOnCollide) {
			registerHandler(z);
		}
		
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
		
		try {
			ScriptHandler.postInit();
		} catch (Exception e) {
			e.printStackTrace();
			SquidAPI.logger.log(e.getStackTrace());
			SquidAPI.messages.add(e.getClass().getName());
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
	public void disable() {
		for (Object object: handlers) {
			MinecraftForge.EVENT_BUS.unregister(object);
		}
		for (Object object: handlers2) {
			FMLCommonHandler.instance().bus().unregister(object);
		}
		mod.setEnabledState(false);
		LogHelper.info("SquidUtils has been disabled.");
	}
}