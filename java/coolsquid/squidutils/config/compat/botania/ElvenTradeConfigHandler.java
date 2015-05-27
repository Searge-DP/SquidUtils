/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.botania;

import java.io.File;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import vazkii.botania.api.BotaniaAPI;

import com.google.common.collect.Lists;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.StringParser;

public class ElvenTradeConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new ElvenTradeConfigHandler(new File("./config/SquidUtils/compat/Botania/ElvenTradeRecipes.cfg"));

	private ElvenTradeConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		ConfigCategory customElvenTradeRecipes = this.config.getCategory("customElvenTradeRecipes");
		for (ConfigCategory category: customElvenTradeRecipes.getChildren()) {
			List<Object> inputs = Lists.newArrayList();
			ItemStack output = null;
			for (Property property: category.getOrderedValues()) {
				if (property.getName().startsWith("input:")) {
					if (Item.itemRegistry.containsKey(property.getString())) {
						inputs.add(new ItemStack((Item) Item.itemRegistry.getObject(property.getString())));
					}
					else {
						inputs.add(property.getString());
					}
				}
				else if (property.getName().equals("output")) {
					output = StringParser.parseItemStack(property.getString());
				}
			}
			BotaniaAPI.registerElvenTradeRecipe(output, inputs.toArray());
		}
	}
}