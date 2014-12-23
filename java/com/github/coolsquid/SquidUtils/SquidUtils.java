package com.github.coolsquid.SquidUtils;

import java.io.File;

import javax.swing.JOptionPane;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;
import com.github.coolsquid.SquidUtils.Utils.EnvironmentChecks;
import com.github.coolsquid.SquidUtils.Utils.Exception.DO_NOT_REPORT_EXCEPTION;
import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

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
		
		EnvironmentChecks.preInit();
		
		File configFile = event.getSuggestedConfigurationFile();
		ConfigHandler.preInit(configFile);
		
		if (!(ConfigHandler.Password.isEmpty())) {
			String p = JOptionPane.showInputDialog("Password:");
			if (!p.equals(ConfigHandler.Password)) {
				throw new DO_NOT_REPORT_EXCEPTION("Wrong password");
			}
		}
		
		LogHelper.info("Preinitialization finished.");
	}
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing...");
		ConfigHandler.postInit();
		LogHelper.info("Postinitialization finished.");
	}
}