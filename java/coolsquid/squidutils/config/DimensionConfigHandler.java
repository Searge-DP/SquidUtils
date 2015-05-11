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
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;

public class DimensionConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new DimensionConfigHandler(new File("./config/SquidUtils/Dimensions.cfg"));

	private DimensionConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (WorldServer world: DimensionManager.getWorlds()) {
			WorldProvider provider = world.provider;
			String name = "dim" + provider.dimensionId;
			provider.hasNoSky = !this.config.get(name, "skies", !provider.hasNoSky).getBoolean();
		}
	}
}