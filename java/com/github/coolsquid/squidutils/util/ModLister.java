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

	public static final ModLister INSTANCE = new ModLister();

	private ModLister() {
		
	}

	private final ILogger logger = new Logger(new File("modlist.txt"));
	
	public void init() {
		if (ConfigHandler.INSTANCE.generateModList == 0) {
			return;
		}
		else if (ConfigHandler.INSTANCE.generateModList == 1) {
			this.generateListOfModids();
		}
		else if (ConfigHandler.INSTANCE.generateModList == 2) {
			this.generateListOfModidsAndVersions();
		}
	}
	
	public void generateListOfModids() {
		LogHelper.info("Generating modlist...");
		FileHelper.writeLines(new File("modlist.txt"), this.getModids());
		LogHelper.info("Generated modlist.");
	}

	public void generateListOfModidsAndVersions() {
		LogHelper.info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			this.logger.log(Utils.newString(Loader.instance().getModList().get(a).getModId(), " ", Loader.instance().getModList().get(a).getVersion()));
		}
		LogHelper.info("Generated modlist.");
	}
	
	public List<String> getModids() {
		return Lists.newArrayList(Loader.instance().getIndexedModList().keySet());
	}
}