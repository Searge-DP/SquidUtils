/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.util.MiscLib;

public class EnchantmentConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new EnchantmentConfigHandler(new File("./config/SquidUtils/Enchantments.cfg"));

	private EnchantmentConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Enchantment enchantment: Enchantment.enchantmentsList) {
			if (enchantment != null && MiscLib.getBlacklister(enchantment) == null) {
				String name = enchantment.name;
				if (name != null && !name.isEmpty()) {
					enchantment.type = EnumEnchantmentType.valueOf(this.config.get(name, "type", enchantment.type.toString()).getString());
					enchantment.weight = this.config.get(name, "weight", enchantment.weight).getInt();
				}
			}
		}
	}
}