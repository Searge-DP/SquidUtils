/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.reflection.ReflectionHelper;

public class TiConToolMaterialConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new TiConToolMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/ToolMaterials.cfg"));

	public TiConToolMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (String name: TConstructRegistry.toolMaterialStrings.keySet()) {
			ToolMaterial object = TConstructRegistry.toolMaterialStrings.get(name);
			ReflectionHelper material = ReflectionHelper.in(object);
			int attack = this.config.get(name, "attack", object.attack).getInt();
			int durability = this.config.get(name, "durability", object.durability).getInt();
			float handleModifier = (float) this.config.get(name, "handleModifier", object.handleModifier).getDouble();
			int harvestLevel = this.config.get(name, "harvestLevel", object.harvestLevel).getInt();
			int miningspeed = this.config.get(name, "miningspeed", object.miningspeed).getInt();
			int primaryColor = this.config.get(name, "primaryColor", object.primaryColor).getInt();
			int reinforced = this.config.get(name, "reinforced", object.reinforced).getInt();
			if (attack != object.attack) {
				material.finalField("attack", "attack").set(attack);
			}
			if (durability != object.durability) {
				material.finalField("durability", "durability").set(durability);
			}
			if (handleModifier != object.handleModifier) {
				material.finalField("handleModifier", "handleModifier").set(handleModifier);
			}
			if (harvestLevel != object.harvestLevel) {
				material.finalField("harvestLevel", "harvestLevel").set(harvestLevel);
			}
			if (miningspeed != object.miningspeed) {
				material.finalField("miningspeed", "miningspeed").set(miningspeed);
			}
			if (primaryColor != object.primaryColor) {
				material.finalField("primaryColor", "primaryColor").set(primaryColor);
			}
			if (reinforced != object.reinforced) {
				material.finalField("reinforced", "reinforced").set(reinforced);
			}
		}
	}
}