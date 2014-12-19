package com.github.coolsquid.SquidUtils;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.SquidUtils.Handlers.EventLogger;
import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;
import com.github.coolsquid.SquidUtils.Utils.Reference;

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

@Mod(modid = Reference.modid, name = Reference.modid, version = Reference.version, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	@EventHandler
	private static void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing");
		
		MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
		
		if (Loader.MC_VERSION.equals("1.7.2")) {
			LogHelper.bigWarning("MC is running 1.7.2! Problems may occur.");
		}
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.preInit(configFile);
	}
	
	@EventHandler
	private static void postInit(FMLPostInitializationEvent event) {
		ConfigHandler.postInit();
	}
}