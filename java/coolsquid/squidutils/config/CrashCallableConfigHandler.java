/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.util.Utils;
import cpw.mods.fml.common.ICrashCallable;

public class CrashCallableConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new CrashCallableConfigHandler(new File("./config/SquidUtils/CrashCallables.cfg"));

	private CrashCallableConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		List<ICrashCallable> list = Utils.getCrashCallables();
		for (int a = 0; a < list.size(); a++) {
			ICrashCallable callable = list.get(a);
			if (!callable.getClass().getName().startsWith("coolsquid.")) {
				String name = callable.getLabel().replace(":", "").replace(" ", "");
				if (!this.config.get(name, "enable", true).getBoolean()) {
					list.remove(a);
				}
			}
		}
	}
}