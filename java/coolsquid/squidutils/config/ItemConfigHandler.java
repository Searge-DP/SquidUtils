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
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.Lists;

import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.handlers.ToolTipHandler;

public class ItemConfigHandler {

	public static final ItemConfigHandler INSTANCE = new ItemConfigHandler();

	private ItemConfigHandler() {
		
	}

	private File configFile;

	private Configuration config;

	public final void init(File file) {
		this.configFile = file;
		this.createConfig();
		this.readConfig();
	}

	private final void createConfig() {
		if (this.config == null)
			this.config = new Configuration(this.configFile);
	}

	@SuppressWarnings("deprecation")
	private final void readConfig() {
		for (Object object: Item.itemRegistry) {
			if (object != null && object != Blocks.air) {
				String name = Item.itemRegistry.getNameForObject(object);
				Item item = (Item) object;

				item.setMaxStackSize(this.config.get(name, "stacksize", item.getItemStackLimit()).getInt());
				item.setMaxDamage(this.config.get(name, "maxDamage", item.getMaxDamage()).getInt());
				item.setTextureName(this.config.get(name, "texture", Utils.ensureNotNull(item.iconString)).getString());
				if (item.getCreativeTab() == null) {
					this.config.get(name, "creativeTab", "null").getString();
				}
				else {
					item.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", item.getCreativeTab().getTabLabel()).getString()));
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
				String tooltip = this.config.get(name, "tooltip", "").getString();
				if (!tooltip.isEmpty()) {
					List<String> list = Lists.newArrayList();
					for (String a: tooltip.split("/n/")) {
						list.add(a);
					}
					ToolTipHandler.INSTANCE.getTooltips().put(item, list);
				}
			}
		}

		if (this.config.hasChanged()) {
			this.config.save();
		}
	}
}