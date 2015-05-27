/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.EffectInfo;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.handlers.EntityHandler;
import coolsquid.squidutils.util.EntityInfo;

public class MobConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new MobConfigHandler(new File("./config/SquidUtils/Mobs.cfg"));

	private MobConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Object object: EntityList.stringToClassMapping.keySet()) {
			if (MiscLib.getBlacklister(object) == null) {
				String name = (String) object;
				if (!this.config.get(name, "enabled", true).getBoolean()) {
					@SuppressWarnings("unchecked")
					Class<? extends Entity> entityclass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(name);
					EntityHandler.disable.add(entityclass);
				}
				EntityInfo props = new EntityInfo();
				props.absorptionAmount = (float) this.config.get(name, "absorptionAmount", -1).getDouble();
				props.fireResistance = this.config.get(name, "fireResistance", -1).getInt();
				props.invisible = this.config.get(name, "invisible", false).getBoolean();
				props.name = this.config.get(name, "name", "").getString();
				props.isImmuneToFire = this.config.get(name, "isImmuneToFire", false).getBoolean();
				for (String a: this.config.get(name, "effects", new String[] {}).getStringList()) {
					String[] b = a.split(":");
					props.effects.add(new EffectInfo(b[0], b[1], b[2]));
				}
				for (String a: this.config.get(name, "equipment", new String[] {}).getStringList()) {
					String[] b = a.split("=");
					props.equipment.put(Integer.parseInt(b[0]), new ItemStack((Item) Item.itemRegistry.getObject(b[1])));
				}
				if (name.equalsIgnoreCase("creeper")) {
					props.avoidCats = this.config.get(name, "avoidCats", true).getBoolean();
				}
			}
		}
	}
}