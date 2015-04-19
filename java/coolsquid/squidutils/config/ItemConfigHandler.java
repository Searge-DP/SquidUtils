/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Lists;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.handlers.ItemBanHandler;
import coolsquid.squidutils.handlers.ToolTipHandler;

public class ItemConfigHandler extends ConfigHandler {

	public static final ItemConfigHandler INSTANCE = new ItemConfigHandler(new SquidAPIFile("./config/SquidUtils/Items.cfg"));

	private ItemConfigHandler(File file) {
		super(file);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadConfig() {
		for (Object object: Item.itemRegistry) {
			try {
				if (object != null && object != Blocks.air && MiscLib.getBlacklister(object) == null) {
					String name = Item.itemRegistry.getNameForObject(object);
					Item item = (Item) object;
					item.setMaxStackSize(this.config.get(name, "stacksize", item.getItemStackLimit()).getInt());
					item.setMaxDamage(this.config.get(name, "maxDamage", item.getMaxDamage()).getInt());
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
						tool.toolMaterial = ToolMaterial.valueOf(this.config.get(name, "toolMaterial", tool.getToolMaterialName()).getString());
					}
					else if (item instanceof ItemArmor) {
						ItemArmor armor = (ItemArmor) item;
						armor.damageReduceAmount = this.config.get(name, "damageReduceAmount", armor.damageReduceAmount).getInt();
						armor.material = ArmorMaterial.valueOf(this.config.get(name, "armorMaterial", armor.getArmorMaterial().toString()).getString());
					}
					else if (item instanceof ItemSword) {
						ItemSword sword = (ItemSword) item;
						sword.field_150933_b = ToolMaterial.valueOf(this.config.get(name, "toolMaterial", sword.getToolMaterialName()).getString());
					}
					else if (item instanceof ItemHoe) {
						ItemHoe hoe = (ItemHoe) item;
						hoe.theToolMaterial = ToolMaterial.valueOf(this.config.get(name, "toolMaterial", hoe.getToolMaterialName()).getString());
					}
					String tooltip = this.config.get(name, "tooltip", "").getString();
					if (!tooltip.isEmpty()) {
						List<String> list = Lists.newArrayList();
						for (String a: tooltip.split("/n/")) {
							list.add(a);
						}
						ToolTipHandler.INSTANCE.getTooltips().put(item, list);
					}
					if (!this.config.get(name, "enabled", true).getBoolean()) {
						ContentRemover.remove(name, ContentType.RECIPE);
						ContentRemover.remove(name, ContentType.SMELTING);
						ItemBanHandler.bannedItems.add(name);
					}
				}
			} catch (Throwable t) {
				SquidUtils.instance().error(object.getClass().getName());
				SquidUtils.instance().error(t);
			}
		}
	}
}