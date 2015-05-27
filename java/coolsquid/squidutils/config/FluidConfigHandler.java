/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.MiscLib;

public class FluidConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new FluidConfigHandler(new File("./config/SquidUtils/Fluids.cfg"));

	private FluidConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Fluid fluid: FluidRegistry.getRegisteredFluids().values()) {
			if (MiscLib.getBlacklister(fluid) == null) {
				String name = fluid.getName();
				fluid.setDensity(this.config.get(name, "density", fluid.getDensity()).getInt());
				fluid.setGaseous(this.config.get(name, "gaseous", fluid.isGaseous()).getBoolean());
				fluid.setLuminosity(this.config.get(name, "luminosity", fluid.getLuminosity()).getInt());
				fluid.setTemperature(this.config.get(name, "temperature", fluid.getTemperature()).getInt());
				fluid.setViscosity(this.config.get(name, "viscosity", fluid.getViscosity()).getInt());
			}
		}
	}
}