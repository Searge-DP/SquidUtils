/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.config.ModListConfigHandler;
import cpw.mods.fml.common.Loader;

public class ModLister {

	public static final ModLister INSTANCE = new ModLister();

	private ModLister() {

	}

	public void init() {
		if (ModListConfigHandler.INSTANCE.generateModList == 1) {
			this.generateListOfModids();
		}
		else if (ModListConfigHandler.INSTANCE.generateModList == 2) {
			this.generateListOfModidsAndVersions();
		}
	}

	public void generateListOfModids() {
		SquidUtils.instance().info("Generating modlist...");
		IOUtils.writeLines(new File("modlist.txt"), this.getModids());
		SquidUtils.instance().info("Generated modlist.");
	}

	public void generateListOfModidsAndVersions() {
		BufferedWriter w = IOUtils.newWriter(new File("modlist.txt"));
		SquidUtils.instance().info("Generating modlist...");
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			try {
				w.write(StringUtils.newString(Loader.instance().getModList().get(a).getModId(), " ", Loader.instance().getModList().get(a).getVersion()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SquidUtils.instance().info("Generated modlist.");
	}

	public List<String> getModids() {
		return Lists.newArrayList(Loader.instance().getIndexedModList().keySet());
	}
}