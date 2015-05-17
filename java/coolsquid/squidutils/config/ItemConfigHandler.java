/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Lists;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidutils.SquidUtils;

public class ItemConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new ItemConfigHandler(new File("./config/SquidUtils/Items.cfg"));

	private ItemConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Object object: Item.itemRegistry.getKeys()) {
			try {
				if (object != null && MiscLib.getBlacklister(object) == null) {
					String name = (String) object;
					Item item = (Item) Item.itemRegistry.getObject(name);
					if (SquidUtils.COMMON.isDebugMode()) {
						SquidUtils.instance().info(name + " (" + item.getClass().getName() + ')');
					}
					item.maxStackSize = this.config.get(name, "stacksize", item.maxStackSize).getInt();
					item.maxDamage = this.config.get(name, "maxDamage", item.maxDamage).getInt();
					if (MiscLib.CLIENT) {
						item.setTextureName(this.config.get(name, "texture", StringUtils.ensureNotNull(item.iconString)).getString());
						if (item.getCreativeTab() == null) {
							this.config.get(name, "creativeTab", "null").getString();
						}
						else {
							item.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", item.getCreativeTab().getTabLabel()).getString()));
						}
					}
					if (item instanceof ItemFood) {
						ItemFood food = (ItemFood) item;
						String category = name + " - effect";
						int id = this.config.get(category, "potionId", 0).getInt();
						int duration = this.config.get(category, "duration", 0).getInt();
						int amplifier = this.config.get(category, "amplifier", 0).getInt();
						float probability = (float) this.config.get(category, "probability", 0F).getDouble();
						if (probability != 0) {
							food.setPotionEffect(id, duration, amplifier, probability);
						}

						food.healAmount = this.config.get(name, "hungerBars", food.healAmount).getInt();
						food.saturationModifier = (float) this.config.get(name, "saturationModifier", food.saturationModifier).getDouble();
						if (this.config.get(name, "alwaysEdible", food.alwaysEdible).getBoolean()) {
							food.setAlwaysEdible();
						}
						food.isWolfsFavoriteMeat = this.config.get(name, "wolfMeat", food.isWolfsFavoriteMeat()).getBoolean();
						food.itemUseDuration = this.config.get(name, "eatingTime", food.itemUseDuration).getInt();
					}
					else if (item instanceof ItemTool) {
						ItemTool tool = (ItemTool) item;
						tool.damageVsEntity = (float) this.config.get(name, "damageVsEntity", tool.damageVsEntity).getDouble();
						tool.efficiencyOnProperMaterial = (float) this.config.get(name, "efficiency", tool.damageVsEntity).getDouble();
					}
					else if (item instanceof ItemArmor) {
						ItemArmor armor = (ItemArmor) item;
						armor.damageReduceAmount = this.config.get(name, "damageReduceAmount", armor.damageReduceAmount).getInt();
					}
					String tooltip = this.config.get(name, "tooltip", "").getString();
					if (!tooltip.isEmpty()) {
						List<String> list = Lists.newArrayList();
						for (String a: tooltip.split("/n/")) {
							list.add(a);
						}
						SquidUtils.COMMON.addTooltips(item, list);
					}
					if (!this.config.get(name, "enabled", true).getBoolean()) {
						SquidUtils.COMMON.banItem(name);
					}
				}
			} catch (Throwable t) {
				SquidUtils.instance().error(object.getClass().getName());
				SquidUtils.instance().error(t);
			}
		}
	}
}