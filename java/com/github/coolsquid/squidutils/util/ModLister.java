/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import java.io.File;
import java.util.List;

import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.logging.ILogger;
import com.github.coolsquid.squidapi.logging.Logger;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.Loader;

public class ModLister {
	
	private static final ILogger logger = new Logger(new File("modlist.txt"));
	
	public static void init() {
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
	
	public static void generateListOfModids() {
		LogHelper.info("Generating modlist...");
		FileHelper.writeLines(new File("modlist.txt"), getModids());
		LogHelper.info("Generated modlist.");
	}

	public static void generateListOfModidsAndVersions() {
		LogHelper.info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			logger.log(Utils.newString(Loader.instance().getModList().get(a).getModId(), " ", Loader.instance().getModList().get(a).getVersion()));
		}
		LogHelper.info("Generated modlist.");
	}
	
	public static List<String> getModids() {
		return Lists.newArrayList(Loader.instance().getIndexedModList().keySet());
	}
}