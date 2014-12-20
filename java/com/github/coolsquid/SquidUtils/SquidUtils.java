package com.github.coolsquid.SquidUtils;

import java.io.File;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

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

@Mod(modid = Data.modid, name = Data.modid, version = Data.version, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	@EventHandler
	private static void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing");
		
		if (Loader.MC_VERSION.equals("1.7.2")) {
			LogHelper.bigWarning(Level.WARN, "MC is running 1.7.2! Problems may occur.");
		}
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.preInit(configFile);
		
		LogHelper.info("Preinitialization finished.");
	}
	
	@EventHandler
	private static void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing");
		ConfigHandler.postInit();
		LogHelper.info("Postinitialization finished.");
	}
}