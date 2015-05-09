/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.world.WorldType;
import coolsquid.squidapi.config.ConfigHandler;

public class WorldTypeConfigHandler extends ConfigHandler {

	public static final WorldTypeConfigHandler INSTANCE = new WorldTypeConfigHandler(new File("./config/SquidUtils/WorldTypes.cfg"));

	public WorldTypeConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (WorldType type: WorldType.worldTypes) {
			if (type != null) {
				if (!this.config.get(type.getWorldTypeName(), "enable", true).getBoolean()) {
					type.canBeCreated = false;
				}
			}
		}
	}
}