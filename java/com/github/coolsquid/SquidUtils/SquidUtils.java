package com.github.coolsquid.SquidUtils;

import java.io.File;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;
import com.github.coolsquid.SquidUtils.Utils.EnvironmentChecks;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

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

@Mod(modid = Data.modid, name = Data.modid, version = Data.version, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing...");
		LogHelper.info(System.getProperty("java.version"));
		EnvironmentChecks.preInit();
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.preInit(configFile);
		
		LogHelper.info("Preinitialization finished.");
	}
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing...");
		ConfigHandler.postInit();
		LogHelper.info("Postinitialization finished.");
	}
}