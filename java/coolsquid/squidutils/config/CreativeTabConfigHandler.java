/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.creativetab.ITab;
import coolsquid.squidapi.util.io.SquidAPIFile;

public class CreativeTabConfigHandler extends ConfigHandler {

	public static final CreativeTabConfigHandler INSTANCE = new CreativeTabConfigHandler(new SquidAPIFile("./config/SquidUtils/CreativeTabs.cfg"));

	private CreativeTabConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ConfigCategory category: this.config.getCategory("customTabs").getChildren()) {
			new ITab(category.getName(), (Item) Item.itemRegistry.getObject(category.get("icon").getString()));
		}
		for (CreativeTabs tab: CreativeTabs.creativeTabArray) {
			String name = tab.getTabLabel();
			tab.setBackgroundImageName(this.config.get(name, "background", tab.getBackgroundImageName()).getString());
			if (!this.config.get(name, "scrollbar", true).getBoolean()) {
				tab.setNoScrollbar();
			}
			if (!this.config.get(name, "displayTitle", true).getBoolean()) {
				tab.setNoTitle();
			}
		}
	}
}