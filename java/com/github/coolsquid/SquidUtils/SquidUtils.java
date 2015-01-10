package com.github.coolsquid.SquidUtils;

import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.CommonHandler;
import com.github.coolsquid.SquidUtils.Utils.Data;
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

@Mod(modid = Data.modid, name = Data.name, version = Data.version, dependencies = Data.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils {
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing...");
		
		CommonHandler.init();
		
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		
		LogHelper.info("Preinitialization finished.");
	}
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing...");
		ConfigHandler.postInit();
		LogHelper.info("Postinitialization finished.");
	}
}