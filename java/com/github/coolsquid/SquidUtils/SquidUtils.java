package com.github.coolsquid.SquidUtils;

import java.io.File;

import com.github.coolsquid.SquidUtils.Handlers.ConfigHandler;
import com.github.coolsquid.SquidUtils.Handlers.LogHandler;

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
		ConfigHandler.createConfig(configFile);
	}
	
	@EventHandler
	private static void PostInit(FMLPostInitializationEvent event) {
		
	}
}