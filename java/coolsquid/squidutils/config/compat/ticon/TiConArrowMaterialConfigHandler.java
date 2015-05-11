/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ArrowMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.reflection.ReflectionHelper;

public class TiConArrowMaterialConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new TiConArrowMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/ArrowMaterials.cfg"));

	public TiConArrowMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int id: TConstructRegistry.arrowMaterials.keySet()) {
			String name = id + "";
			ArrowMaterial object = TConstructRegistry.arrowMaterials.get(id);
			ReflectionHelper material = ReflectionHelper.in(object);
			float breakChance = (float) this.config.get(name, "breakChance", object.breakChance).getDouble();
			float mass = (float) this.config.get(name, "mass", object.mass).getDouble();
			if (breakChance != object.breakChance) {
				material.finalField("breakChance", "breakChance").set(breakChance);
			}
			if (mass != object.mass) {
				material.finalField("mass", "mass").set(mass);
			}
		}
	}
}