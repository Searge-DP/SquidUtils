/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import coolsquid.lib.hooks.WorldGenHooks;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.MiscLib;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new WorldGenConfigHandler(new File("./config/SquidUtils/WorldGen.cfg"));

	public WorldGenConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (IWorldGenerator i: WorldGenHooks.getWorldGenerators()) {
			if (MiscLib.getBlacklister(i) == null) {
				if (!this.config.get(i.getClass().getName().replace('.', '/'), "enable", true).getBoolean()) {
					WorldGenHooks.getWorldGenerators().remove(i);
				}
			}
		}
	}
}