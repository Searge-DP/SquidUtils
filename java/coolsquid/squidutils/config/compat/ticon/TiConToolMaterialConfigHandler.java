/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.ticon;

import java.io.File;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidapi.config.ConfigHandler;

public class TiConToolMaterialConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new TiConToolMaterialConfigHandler(new File("./config/SquidUtils/compat/TiCon/ToolMaterials.cfg"));

	public TiConToolMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() throws ReflectiveOperationException {
		for (String name: TConstructRegistry.toolMaterialStrings.keySet()) {
			ToolMaterial object = TConstructRegistry.toolMaterialStrings.get(name);
			int attack = this.config.get(name, "attack", object.attack).getInt();
			int durability = this.config.get(name, "durability", object.durability).getInt();
			float handleModifier = (float) this.config.get(name, "handleModifier", object.handleModifier).getDouble();
			int harvestLevel = this.config.get(name, "harvestLevel", object.harvestLevel).getInt();
			int miningspeed = this.config.get(name, "miningspeed", object.miningspeed).getInt();
			int primaryColor = this.config.get(name, "primaryColor", object.primaryColor).getInt();
			int reinforced = this.config.get(name, "reinforced", object.reinforced).getInt();
			if (attack != object.attack) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "attack", attack);
			}
			if (durability != object.durability) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "durability", durability);
			}
			if (handleModifier != object.handleModifier) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "handleModifier", handleModifier);
			}
			if (harvestLevel != object.harvestLevel) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "harvestLevel", harvestLevel);
			}
			if (miningspeed != object.miningspeed) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "miningspeed", miningspeed);
			}
			if (primaryColor != object.primaryColor) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "primaryColor", primaryColor);
			}
			if (reinforced != object.reinforced) {
				ReflectionHelper.setPublicFinalValue(ToolMaterial.class, object, "reinforced", reinforced);
			}
		}
	}
}