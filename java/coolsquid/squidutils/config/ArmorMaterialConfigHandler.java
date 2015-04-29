/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.Set;

import net.minecraft.item.ItemArmor.ArmorMaterial;

import com.google.common.collect.Sets;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;

public class ArmorMaterialConfigHandler extends ConfigHandler {

	public static final ArmorMaterialConfigHandler INSTANCE = new ArmorMaterialConfigHandler(new SquidAPIFile("./config/SquidUtils/ArmorMaterials.cfg"));
	private static final Set<ArmorMaterial> materials = Sets.newHashSet(ArmorMaterial.CHAIN, ArmorMaterial.CLOTH, ArmorMaterial.DIAMOND, ArmorMaterial.GOLD, ArmorMaterial.IRON);

	private ArmorMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ArmorMaterial material: materials) {
			String name = material.toString();
			if (SquidUtils.COMMON.isDebugMode()) {
				SquidUtils.instance().info(name);
			}
			material.enchantability = this.config.get(name, "enchantability", material.enchantability).getInt();
			material.damageReductionAmountArray = this.config.get(name, "damageReduction", material.damageReductionAmountArray).getIntList();
			material.maxDamageFactor = this.config.get(name, "material", material.maxDamageFactor).getInt();
		}
	}
}