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
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidapi.config.ConfigHandler;

public class ChestGenConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new ChestGenConfigHandler(new File("./config/SquidUtils/ChestGen.cfg"));

	private ChestGenConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() throws ReflectiveOperationException {
		HashMap<String, ChestGenHooks> chests = ReflectionHelper.getPrivateStaticValue(ChestGenHooks.class, "chestInfo");
		for (String category: chests.keySet()) {
			ChestGenHooks chest = chests.get(category);
			ArrayList<WeightedRandomChestContent> contents = ReflectionHelper.getPrivateValue(ChestGenHooks.class, chest, "contents");
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
	}
}