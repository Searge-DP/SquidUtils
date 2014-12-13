package com.github.coolsquid.SquidUtils;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.SquidUtils.Handlers.ExceptionHandler;
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
 *
 */

@Mod(modid = SquidUtils.modid, name = "SquidUtils", version = SquidUtils.version, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	public static final String modid = "SquidUtils";
	public static final String version = "1.0.6";
	
	@EventHandler
	private static void PreInit(FMLPreInitializationEvent event) {
		
		LogHandler.info("Preinitializing");
		
		if (Loader.MC_VERSION.equals("1.7.2")) {
			LogHandler.bigWarning("MC is running 1.7.2! Problems may occur.");
		}
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.createConfig(configFile);
		
		String dir = System.getProperty("user.dir");
		
		int A = 0;
		while (A < ExceptionHandler.size()) {
			if (dir.contains(ExceptionHandler.get(A))) {
				MinecraftForge.EVENT_BUS.register((Object)new ExceptionHandler());
			}
			A++;
		}
	}
}