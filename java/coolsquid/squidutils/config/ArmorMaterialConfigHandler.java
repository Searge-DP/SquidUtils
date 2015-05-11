/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidutils.SquidUtils;

public class ArmorMaterialConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new ArmorMaterialConfigHandler(new File("./config/SquidUtils/ArmorMaterials.cfg"));

	private ArmorMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ArmorMaterial material: ArmorMaterial.values()) {
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