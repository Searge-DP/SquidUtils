/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ArrowMaterial;
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidapi.config.ConfigHandler;

public class TiConArrowMaterialConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new TiConArrowMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/ArrowMaterials.cfg"));

	public TiConArrowMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() throws ReflectiveOperationException {
		for (int id: TConstructRegistry.arrowMaterials.keySet()) {
			String name = id + "";
			ArrowMaterial object = TConstructRegistry.arrowMaterials.get(id);
			float breakChance = (float) this.config.get(name, "breakChance", object.breakChance).getDouble();
			float mass = (float) this.config.get(name, "mass", object.mass).getDouble();
			if (breakChance != object.breakChance) {
				ReflectionHelper.setPublicFinalValue(ArrowMaterial.class, object, "breakChance", breakChance);
			}
			if (mass != object.mass) {
				ReflectionHelper.setPublicFinalValue(ArrowMaterial.class, object, "mass", mass);
			}
		}
	}
}