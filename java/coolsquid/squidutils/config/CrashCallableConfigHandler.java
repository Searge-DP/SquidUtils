/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.MiscLib;
import cpw.mods.fml.common.ICrashCallable;

public class CrashCallableConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new CrashCallableConfigHandler(new File("./config/SquidUtils/CrashCallables.cfg"));

	private CrashCallableConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int a = 0; a < MiscLib.CRASH_CALLABLES.size(); a++) {
			ICrashCallable callable = MiscLib.CRASH_CALLABLES.get(a);
			if (!callable.getClass().getName().startsWith("coolsquid.")) {
				String name = callable.getLabel().replace(":", "").replace(" ", "");
				if (!this.config.get(name, "enable", true).getBoolean()) {
					MiscLib.CRASH_CALLABLES.remove(a);
				}
			}
		}
	}
}