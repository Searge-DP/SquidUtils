/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.reflection.ReflectionHelper;
import coolsquid.squidapi.util.io.SquidAPIFile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;

public class CrashCallableConfigHandler extends ConfigHandler {

	public static final CrashCallableConfigHandler INSTANCE = new CrashCallableConfigHandler(new SquidAPIFile("./config/SquidUtils/CrashCallables.cfg"));

	private CrashCallableConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		List<ICrashCallable> callables = ReflectionHelper.in(FMLCommonHandler.instance()).field("crashCallables", "crashCallables").get();
		for (int a = 0; a < callables.size(); a++) {
			ICrashCallable callable = callables.get(a);
			if (!callable.getClass().getName().startsWith("coolsquid.")) {
				String name = callable.getLabel().replace(":", "").replace(" ", "");
				if (!this.config.get(name, "enable", true).getBoolean()) {
					callables.remove(a);
				}
			}
		}
	}
}