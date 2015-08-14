/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomFishable;
import coolsquid.lib.hooks.FishingHooks;
import coolsquid.squidapi.config.ConfigHandler;

public class FishingConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new FishingConfigHandler(new File("./config/SquidUtils/Fishing.cfg"));

	private FishingConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		this.load("fish", FishingHooks.getFish());
		this.load("junk", FishingHooks.getJunk());
		this.load("treasure", FishingHooks.getTreasure());
	}

	private void load(String category, List<WeightedRandomFishable> list) {
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
}