/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.item.Item.ToolMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;

public class ToolMaterialConfigHandler extends ConfigHandler {

	public static final ToolMaterialConfigHandler INSTANCE = new ToolMaterialConfigHandler(new SquidAPIFile("./config/SquidUtils/ToolMaterials.cfg"));

	private ToolMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ToolMaterial material: ToolMaterial.values()) {
			String name = material.toString();
			try {
				material.maxUses = this.config.get(name, "durability", material.maxUses).getInt();
				material.harvestLevel = this.config.get(name, "harvestLevel", material.harvestLevel).getInt();
				material.enchantability = this.config.get(name, "enchantability", material.enchantability).getInt();
				material.efficiencyOnProperMaterial = (float) this.config.get(name, "efficiency", material.efficiencyOnProperMaterial).getDouble();
				material.damageVsEntity = (float) this.config.get(name, "attackDamage", material.damageVsEntity).getDouble();
			} catch (Exception e) {
				SquidUtils.instance().error(name);
				throw e;
			}
		}
	}
}