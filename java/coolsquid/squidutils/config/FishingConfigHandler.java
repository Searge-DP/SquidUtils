/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.config.ConfigCategory;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.helpers.FishingHelper;
import coolsquid.squidapi.util.io.SquidAPIFile;

public class FishingConfigHandler extends ConfigHandler {

	public static final FishingConfigHandler INSTANCE = new FishingConfigHandler(new SquidAPIFile("./config/SquidUtils/Fishing.cfg"));

	private FishingConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (String category: FishingHelper.getCategories().keySet()) {
			List<WeightedRandomFishable> list = FishingHelper.getCategories().get(category);
			for (int a = 0; a < list.size(); a++) {
				WeightedRandomFishable fish = list.get(a);
				String name = category + "." + Item.itemRegistry.getNameForObject(fish.field_150711_b.getItem());
				if (!this.config.get(name, "enable", true).getBoolean()) {
					list.remove(a);
				}
				fish.field_150710_d = this.config.get(name, "enchanted", fish.field_150710_d).getBoolean();
				fish.field_150712_c = (float) this.config.get(name, "damage", fish.field_150712_c).getDouble();
			}
		}
		ConfigCategory custom = this.config.getCategory("custom");
		for (ConfigCategory fish: custom.getChildren()) {
			for (ConfigCategory cat: fish.getChildren()) {
				if (fish.getName().equals("fish")) {
					FishingHelper.addFish(new ItemStack((Item) Item.itemRegistry.getObject(cat.getName())), cat.get("weight").getInt());
				}
				else if (fish.getName().equals("junk")) {
					FishingHelper.addJunk(new ItemStack((Item) Item.itemRegistry.getObject(cat.getName())), cat.get("weight").getInt());
				}
				else if (fish.getName().equals("treasure")) {
					FishingHelper.addTreasure(new ItemStack((Item) Item.itemRegistry.getObject(cat.getName())), cat.get("weight").getInt());
				}
			}
		}
	}
}