/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.uptodate;

import java.io.File;
import java.util.Map;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.DataSorter;
import coolsquid.squidapi.util.version.UpToDateable;
import cpw.mods.fml.common.Loader;

public class UpToDateConfigHandler extends ConfigHandler {

	public static final UpToDateConfigHandler INSTANCE = new UpToDateConfigHandler(new File("./config/SquidUtils/UpToDate.cfg"));

	public UpToDateConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (String string: this.config.getStringList("mods", "general", new String[] {}, "")) {
			Map<String, String> data = DataSorter.sort(string, "modid", "name", "url");
			String modid = data.get("modid");
			String name = data.containsKey("name") ? data.get("name") : modid;
			if (Loader.isModLoaded(modid)) {
				UpToDateable uptodater = new UpToDateable(name, Loader.instance().getIndexedModList().get(modid).getVersion(), data.get("url"));
				uptodater.register();
			}
		}
	}
}