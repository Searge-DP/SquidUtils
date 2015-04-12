/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import coolsquid.squidapi.helpers.FileHelper;
import coolsquid.squidapi.logging.Logger;
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.config.GeneralConfigHandler;
import cpw.mods.fml.common.Loader;

public class ModLister {

	public static final ModLister INSTANCE = new ModLister();

	private ModLister() {
		
	}

	private final Logger logger = new Logger(new File("modlist.txt"));
	
	public void init() {
		if (GeneralConfigHandler.INSTANCE.generateModList == 0) {
			return;
		}
		else if (GeneralConfigHandler.INSTANCE.generateModList == 1) {
			this.generateListOfModids();
		}
		else if (GeneralConfigHandler.INSTANCE.generateModList == 2) {
			this.generateListOfModidsAndVersions();
		}
	}
	
	public void generateListOfModids() {
		SquidUtils.instance().info("Generating modlist...");
		FileHelper.writeLines(new File("modlist.txt"), this.getModids());
		SquidUtils.instance().info("Generated modlist.");
	}

	public void generateListOfModidsAndVersions() {
		SquidUtils.instance().info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			this.logger.log(StringUtils.newString(Loader.instance().getModList().get(a).getModId(), " ", Loader.instance().getModList().get(a).getVersion()));
		}
		SquidUtils.instance().info("Generated modlist.");
	}
	
	public List<String> getModids() {
		return Lists.newArrayList(Loader.instance().getIndexedModList().keySet());
	}
}