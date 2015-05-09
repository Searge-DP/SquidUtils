/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.Utils;
import cpw.mods.fml.common.ICrashCallable;

public class CrashCallableConfigHandler extends ConfigHandler {

	public static final CrashCallableConfigHandler INSTANCE = new CrashCallableConfigHandler(new File("./config/SquidUtils/CrashCallables.cfg"));

	private CrashCallableConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int a = 0; a < Utils.getCrashCallables().size(); a++) {
			ICrashCallable callable = Utils.getCrashCallables().get(a);
			if (!callable.getClass().getName().startsWith("coolsquid.")) {
				String name = callable.getLabel().replace(":", "").replace(" ", "");
				if (!this.config.get(name, "enable", true).getBoolean()) {
					Utils.getCrashCallables().remove(a);
				}
			}
		}
	}
}