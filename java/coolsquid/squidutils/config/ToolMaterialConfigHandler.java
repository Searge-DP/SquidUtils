/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.item.Item.ToolMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidutils.SquidUtils;

public class ToolMaterialConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new ToolMaterialConfigHandler(new File("./config/SquidUtils/ToolMaterials.cfg"));

	private ToolMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		boolean b = false;
		for (ToolMaterial material: ToolMaterial.values()) {
			if (material.name().toUpperCase().equals("RA:FLUXELECTRUM")) {
				config.getCategory("RA:FLUXELECTRUM").setComment("RedstoneArsenal tool properties cannot be configured at the moment. I'm looking into it!");
				b = true;
				continue;
			}
			String name = material.toString();
			if (SquidUtils.COMMON.isDebugMode()) {
				SquidUtils.log.info(name);
			}
			material.maxUses = this.config.get(name, "durability", material.maxUses).getInt();
			material.harvestLevel = this.config.get(name, "harvestLevel", material.harvestLevel).getInt();
			material.enchantability = this.config.get(name, "enchantability", material.enchantability).getInt();
			material.efficiencyOnProperMaterial = (float) this.config.get(name, "efficiency", material.efficiencyOnProperMaterial).getDouble();
			material.damageVsEntity = (float) this.config.get(name, "attackDamage", material.damageVsEntity).getDouble();
		}
		if (!config.hasChanged() && b) {
			config.save();
		}
	}
}