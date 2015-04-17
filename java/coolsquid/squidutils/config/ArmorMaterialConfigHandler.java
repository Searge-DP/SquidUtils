/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;

public class ArmorMaterialConfigHandler extends ConfigHandler {

	public static final ArmorMaterialConfigHandler INSTANCE = new ArmorMaterialConfigHandler(new SquidAPIFile("./config/SquidUtils/ArmorMaterials.cfg"));

	private ArmorMaterialConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ArmorMaterial material: ArmorMaterial.values()) {
			String name = material.toString();
			if (isBlacklisted(name)) {
				try {
					material.enchantability = this.config.get(name, "enchantability", material.enchantability).getInt();
					material.damageReductionAmountArray = this.config.get(name, "damageReduction", material.damageReductionAmountArray).getIntList();
					material.maxDamageFactor = this.config.get(name, "material", material.maxDamageFactor).getInt();
				} catch (Exception e) {
					SquidUtils.instance().error(name);
					throw e;
				}
			}
		}
	}

	private static boolean isBlacklisted(String name) {
		return name.matches("(Bedrock)");
	}
}