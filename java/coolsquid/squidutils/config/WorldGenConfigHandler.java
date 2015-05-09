/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.helpers.WorldGenHelper;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenConfigHandler extends ConfigHandler {

	public static final WorldGenConfigHandler INSTANCE = new WorldGenConfigHandler(new File("./config/SquidUtils/WorldGen.cfg"));

	public WorldGenConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (IWorldGenerator i: WorldGenHelper.worldGenerators) {
			if (MiscLib.getBlacklister(i) == null) {
				if (!this.config.get(i.getClass().getName().replace('.', '/'), "enable", true).getBoolean()) {
					WorldGenHelper.remove(i);
				}
			}
		}
	}
}