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
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.SquidUtils;

public class CreativeTabConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new CreativeTabConfigHandler(new File("./config/SquidUtils/CreativeTabs.cfg"));

	private CreativeTabConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		if (MiscLib.CLIENT) {
			for (ConfigCategory category: this.config.getCategory("customTabs").getChildren()) {
				new ITab(category.getName(), (Item) Item.itemRegistry.getObject(category.get("icon").getString()));
			}
			for (CreativeTabs tab: CreativeTabs.creativeTabArray) {
				String name = tab.getTabLabel();
				if (SquidUtils.COMMON.isDebugMode()) {
					SquidUtils.instance().info(name + " (" + tab.getClass().getName() + ')');
				}
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
}