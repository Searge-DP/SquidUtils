/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.uptodate;

import java.io.File;
import java.util.Map;

import pt.uptodate.FetchedUpdateable;
import pt.uptodate.UpToDate;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.DataSorter;
import coolsquid.squidapi.util.io.WebUtils;
import coolsquid.squidapi.util.math.IntUtils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

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
				ModContainer mod = Loader.instance().getIndexedModList().get(modid);
				UpToDate.registerFetched(newFetchedUpdateable(name, mod.getVersion(), mod.getMetadata().url, data.get("url")));
			}
		}
	}

	private static FetchedUpdateable newFetchedUpdateable(String name, String version, String friendlyUrl, String url) {
		Map<String, String> data = DataSorter.sort(WebUtils.readAll(url), "version", "severity", "url");
		String remoteVersion = data.get("version");
		int severity = IntUtils.parseInt(data.get("severity"));
		friendlyUrl = data.containsKey("url") ? data.get("url") : friendlyUrl;
		return new FetchedUpdateable(false, severity, remoteVersion, version, url, IntUtils.parseInt(version), IntUtils.parseInt(remoteVersion), name);
	}
}