/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.registry.MaterialRegistry;
import coolsquid.squidapi.util.io.SquidAPIFile;

public class BlockMaterialConfigHandler extends ConfigHandler {

	public static final BlockMaterialConfigHandler INSTANCE = new BlockMaterialConfigHandler(new SquidAPIFile("./config/SquidUtils/BlockMaterials.cfg"));

	private BlockMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Material material: MaterialRegistry.INSTANCE) {
			String name = MaterialRegistry.INSTANCE.getName(material);
			material.canBurn = this.config.get(name, "canBurn", material.canBurn).getBoolean();
			material.isAdventureModeExempt = this.config.get(name, "isAdventureModeExempt", material.isAdventureModeExempt).getBoolean();
			material.isTranslucent = this.config.get(name, "isTranslucent", material.isTranslucent).getBoolean();
			material.materialMapColor = MapColor.mapColorArray[this.config.get(name, "color", material.materialMapColor.colorIndex).getInt()];
			material.replaceable = this.config.get(name, "replaceable", material.replaceable).getBoolean();
			material.requiresNoTool = this.config.get(name, "requiresNoTool", material.requiresNoTool).getBoolean();
			material.mobilityFlag = this.config.get(name, "mobilityFlag", material.mobilityFlag).getInt();
		}
	}
}