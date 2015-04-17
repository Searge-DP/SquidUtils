/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;

public class DimensionConfigHandler extends ConfigHandler {

	public static final DimensionConfigHandler INSTANCE = new DimensionConfigHandler(new SquidAPIFile("./config/SquidUtils/Dimensions.cfg"));

	private DimensionConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (WorldServer world: DimensionManager.getWorlds()) {
			WorldProvider provider = world.provider;
			String name = provider.dimensionId + "";
			provider.hasNoSky = !this.config.get(name, "skies", !provider.hasNoSky).getBoolean();
		}
	}
}