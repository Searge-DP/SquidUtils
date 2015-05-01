/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.BowMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.reflection.ReflectionHelper;

public class TiConBowMaterialConfigHandler extends ConfigHandler {

	public static final TiConBowMaterialConfigHandler INSTANCE = new TiConBowMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/BowMaterials.cfg"));

	public TiConBowMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int id: TConstructRegistry.bowMaterials.keySet()) {
			String name = id + "";
			BowMaterial object = TConstructRegistry.bowMaterials.get(id);
			ReflectionHelper material = ReflectionHelper.in(object);
			int drawspeed = this.config.get(name, "drawspeed", object.drawspeed).getInt();
			if (drawspeed != object.drawspeed) {
				material.finalField("drawspeed", "drawspeed").set(drawspeed);
			}
		}
	}
}