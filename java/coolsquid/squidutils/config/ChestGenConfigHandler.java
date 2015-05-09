/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.ConfigCategory;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.reflection.ReflectionHelper;

public class ChestGenConfigHandler extends ConfigHandler {

	public static final ChestGenConfigHandler INSTANCE = new ChestGenConfigHandler(new File("./config/SquidUtils/ChestGen.cfg"));

	private ChestGenConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		HashMap<String, ChestGenHooks> chests = ReflectionHelper.in(ChestGenHooks.class).field("chestInfo", "chestInfo").get();
		for (String category: chests.keySet()) {
			ChestGenHooks chest = chests.get(category);
			ArrayList<WeightedRandomChestContent> contents = ReflectionHelper.in(chest).field("contents", "contents").get();
			for (int a = 0; a < contents.size(); a++) {
				WeightedRandomChestContent content = contents.get(a);
				String name = category + "." + Item.itemRegistry.getNameForObject(content.theItemId.getItem());
				if (!this.config.get(name, "enabled", true).getBoolean()) {
					contents.remove(a);
				}
				content.itemWeight = this.config.get(name, "weight", content.itemWeight).getInt();
				content.theMaximumChanceToGenerateItem = this.config.get(name, "minChance", content.theMaximumChanceToGenerateItem).getInt();
				content.theMinimumChanceToGenerateItem = this.config.get(name, "minChance", content.theMinimumChanceToGenerateItem).getInt();
			}
		}
		String customChestGen = "customChestGen";
		for (ConfigCategory child: this.config.getCategory(customChestGen).getChildren()) {
			for (ConfigCategory category: child.getChildren()) {
				Item item = (Item) Item.itemRegistry.getObject(category.getName());
				int damage = category.get("damage").getInt();
				int minChance = category.get("minAmount").getInt();
				int maxChance = category.get("maxAmount").getInt();
				int weight = category.get("weight").getInt();
				ChestGenHooks.addItem(child.getName(), new WeightedRandomChestContent(item, damage, minChance, maxChance, weight));
			}
		}
	}
}