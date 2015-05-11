/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidapi.helpers.AchievementHelper;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.SquidUtils;

public class AchievementConfigHandler extends ConfigHandlerImpl {

	public static final ConfigHandler INSTANCE = new AchievementConfigHandler(new File("./config/SquidUtils/Achievements.cfg"));

	private AchievementConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int a = 0; a < AchievementList.achievementList.size(); a++) {
			Achievement achievement = (Achievement) AchievementList.achievementList.get(a);
			if (achievement != null && MiscLib.getBlacklister(achievement) == null) {
				String name = achievement.statId;
				if (SquidUtils.COMMON.isDebugMode()) {
					SquidUtils.instance().info(name + " (" + achievement.getClass().getName() + ')');
				}
				achievement.achievementDescription = this.config.get(name, "description", achievement.achievementDescription).getString();
				achievement.displayColumn = this.config.get(name, "displayColumn", achievement.displayColumn).getInt();
				achievement.displayRow = this.config.get(name, "displayRow", achievement.displayRow).getInt();
				achievement.isSpecial = this.config.get(name, "isSpecial", achievement.isSpecial).getBoolean();
				if (achievement.parentAchievement != null) {
					achievement.parentAchievement = AchievementHelper.getAchievement(this.config.get(name, "parent", achievement.parentAchievement.statId).getString());
				}
				achievement.theItemStack = new ItemStack((Item) Item.itemRegistry.getObject(this.config.get(name, "item", Item.itemRegistry.getNameForObject(achievement.theItemStack.getItem())).getString()));
				if (!this.config.get(name, "enable", true).getBoolean()) {
					AchievementList.achievementList.remove(a);
				}
			}
		}
	}
}