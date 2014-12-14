package com.github.coolsquid.SquidUtils;

import java.io.File;

import com.github.coolsquid.SquidUtils.Handlers.DirList;
import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * The main class
 *
 */

@Mod(modid = Reference.modid, name = Reference.modid, version = Reference.version, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	@EventHandler
	private static void PreInit(FMLPreInitializationEvent event) {
		
		LogHandler.info("Preinitializing");
		
		DirList.arrayInit();
		
		if (Loader.MC_VERSION.equals("1.7.2")) {
			LogHandler.bigWarning("MC is running 1.7.2! Problems may occur.");
		}
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.createConfig(configFile);
		
		String dir = System.getProperty("user.dir");
		
		int A = 0;
		while (A < DirList.size()) {
			if (dir.contains(DirList.get(A))) {
				LogHandler.bigWarning("This modpack might be illegal. Please ask for permission at:");
				LogHandler.info(Reference.forum);
				}
			A++;
		}
	}
}
