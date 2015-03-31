/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.Lists;

import coolsquid.squidapi.util.StringParser;
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
				//item.setTextureName(this.config.get(name, "texture", item.getIconString()).getString());
				if (item.getCreativeTab() == null) {
					this.config.get(name, "creativeTab", "null").getString();
				}
				else {
					item.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", item.getCreativeTab().getTabLabel()).getString()));
				}
				if (item instanceof ItemFood) {
					String category = name + " - effect";
					int id = this.config.get(category, "potionId", 0).getInt();
					int duration = this.config.get(category, "duration", 0).getInt();
					int amplifier = this.config.get(category, "amplifier", 0).getInt();
					float probability = (float) this.config.get(category, "probability", 0F).getDouble();
					if (probability != 0) {
						((ItemFood) item).setPotionEffect(id, duration, amplifier, probability);
					}
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