/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import com.github.coolsquid.squidapi.logging.Logger;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.Loader;

public class ModLister {
	
	private static final Logger logger = new Logger("", "modlist.txt");
	
	public static final void init() {
		if (ConfigHandler.generateModList == 0) {
			return;
		}
		else if (ConfigHandler.generateModList == 1) {
			generateListOfModids();
		}
		else if (ConfigHandler.generateModList == 2) {
			generateListOfModidsAndVersions();
		}
	}
	
	public static final void generateListOfModids() {
		LogHelper.info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			logger.log(Loader.instance().getModList().get(a).getModId());
			logger.log("\n");
		}
		LogHelper.info("Generated modlist.");
	}
	
	public static final void generateListOfModidsAndVersions() {
		LogHelper.info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			logger.log(Loader.instance().getModList().get(a).getModId());
			logger.log(" ");
			logger.log(Loader.instance().getModList().get(a).getVersion());
			logger.log("\n");
		}
		LogHelper.info("Generated modlist.");
	}
}