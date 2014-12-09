package com.github.coolsquid.SquidUtils;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.SquidUtils.Handlers.ConfigHandler;
import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
import com.github.coolsquid.SquidUtils.Handlers.RecipeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.AchievementHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.DebugHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.HardDifficulty;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RenderDistanceHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.StackSizeHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.TNTHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.WitherHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

@Mod(modid = "squidutils", name = "SquidUtils", version = "1.0.3", acceptableRemoteVersions = "*", dependencies = "")
public class SquidUtils {

	@EventHandler
	private static void PreInit(FMLPreInitializationEvent event) {
		LogHandler.info("Preinitializing");

		if (Loader.MC_VERSION.equals("1.7.2")) {
			LogHandler.bigWarning("MC is running 1.7.2! Problems may occur.");
		}
				
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.load(configFile);
		config();
	}
	
	private static void config() {
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE")) {
			MinecraftForge.EVENT_BUS.register((Object)new HardDifficulty());
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") || !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") || !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") || !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") || !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			LogHandler.error("Error in the config. F+orceHard has a wrong value.");
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
	}
	
	@EventHandler
	private static void PostInit(FMLPostInitializationEvent event) {
		
	}
}