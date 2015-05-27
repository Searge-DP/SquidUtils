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
import vazkii.botania.api.brew.Brew;

import com.google.common.collect.Lists;

import coolsquid.squidapi.config.ConfigHandler;

public class BrewConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new BrewConfigHandler(new File("./config/SquidUtils/compat/Botania/BrewRecipes.cfg"));

	private BrewConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		ConfigCategory customBrewRecipes = this.config.getCategory("customBrewRecipes");
		for (ConfigCategory category: customBrewRecipes.getChildren()) {
			List<Object> inputs = Lists.newArrayList();
			Brew output = null;
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
					output = BotaniaAPI.getBrewFromKey(property.getString());
				}
			}
			BotaniaAPI.registerBrewRecipe(output, inputs.toArray());
		}
	}
}