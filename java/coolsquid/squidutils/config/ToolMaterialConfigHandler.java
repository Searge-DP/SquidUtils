/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.Set;

import net.minecraft.item.Item.ToolMaterial;

import com.google.common.collect.Sets;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;

public class ToolMaterialConfigHandler extends ConfigHandler {

	public static final ToolMaterialConfigHandler INSTANCE = new ToolMaterialConfigHandler(new SquidAPIFile("./config/SquidUtils/ToolMaterials.cfg"));
	private static final Set<ToolMaterial> materials = Sets.newHashSet(ToolMaterial.STONE, ToolMaterial.WOOD, ToolMaterial.EMERALD, ToolMaterial.GOLD, ToolMaterial.IRON);

	private ToolMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ToolMaterial material: materials) {
			String name = material.toString();
			if (SquidUtils.COMMON.isDebugMode()) {
				SquidUtils.instance().info(name);
			}
			material.maxUses = this.config.get(name, "durability", material.maxUses).getInt();
			material.harvestLevel = this.config.get(name, "harvestLevel", material.harvestLevel).getInt();
			material.enchantability = this.config.get(name, "enchantability", material.enchantability).getInt();
			material.efficiencyOnProperMaterial = (float) this.config.get(name, "efficiency", material.efficiencyOnProperMaterial).getDouble();
			material.damageVsEntity = (float) this.config.get(name, "attackDamage", material.damageVsEntity).getDouble();
		}
	}
}