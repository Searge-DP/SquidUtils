/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.util.DamageSource;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidutils.SquidUtils;

public class DamageSourceConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new DamageSourceConfigHandler(new File("./config/SquidUtils/DamageSources.cfg"));

	private DamageSourceConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (DamageSource source: SquidUtils.API.getDamageSources().values()) {
			String name = source.damageType;
			if (SquidUtils.COMMON.isDebugMode()) {
				SquidUtils.INSTANCE.info(name + " (" + source.getClass().getName() + ')');
			}
			source.damageIsAbsolute = this.config.get(name, "damageIsAbsolute", source.damageIsAbsolute).getBoolean();
			source.explosion = this.config.get(name, "explosion", source.explosion).getBoolean();
			source.fireDamage = this.config.get(name, "fireDamage", source.fireDamage).getBoolean();
			source.isDamageAllowedInCreativeMode = this.config.get(name, "isDamageAllowedInCreativeMode", source.isDamageAllowedInCreativeMode).getBoolean();
			source.isUnblockable = this.config.get(name, "isUnblockable", source.isUnblockable).getBoolean();
			source.magicDamage = this.config.get(name, "magicDamage", source.magicDamage).getBoolean();
			source.projectile = this.config.get(name, "projectile", source.projectile).getBoolean();
			source.hungerDamage = (float) this.config.get(name, "hungerDamage", source.hungerDamage).getDouble();
			if (!this.config.get(name, "enabled", true).getBoolean()) {
				SquidUtils.COMMON.disableDamageSource(source);
			}
		}
	}
}