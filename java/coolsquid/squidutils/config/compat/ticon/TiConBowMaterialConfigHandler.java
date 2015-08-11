/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.BowMaterial;
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidapi.config.ConfigHandler;

public class TiConBowMaterialConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new TiConBowMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/BowMaterials.cfg"));

	public TiConBowMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() throws ReflectiveOperationException {
		for (int id: TConstructRegistry.bowMaterials.keySet()) {
			String name = id + "";
			BowMaterial object = TConstructRegistry.bowMaterials.get(id);
			int drawspeed = this.config.get(name, "drawspeed", object.drawspeed).getInt();
			if (drawspeed != object.drawspeed) {
				ReflectionHelper.setPublicFinalValue(BowMaterial.class, object, "drawspeed", drawspeed);
			}
		}
	}
}