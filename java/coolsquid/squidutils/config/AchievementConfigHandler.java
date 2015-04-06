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
import coolsquid.squidapi.helpers.AchievementHelper;
import coolsquid.squidapi.util.io.SquidAPIFile;

public class AchievementConfigHandler extends ConfigHandler {

	public static final AchievementConfigHandler INSTANCE = new AchievementConfigHandler(new SquidAPIFile("./config/SquidUtils/Achievements.cfg"));

	private AchievementConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (int a = 0; a < AchievementList.achievementList.size(); a++) {
			Achievement achievement = (Achievement) AchievementList.achievementList.get(a);
			String name = achievement.statId;
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