/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.IdentityHashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.asm.Hooks;

public class ModConfigHandler extends ConfigHandler {

	public static final ModConfigHandler INSTANCE = new ModConfigHandler(new File("./config/SquidUtils/SquidUtils.cfg"));

	private ModConfigHandler(File file) {
		super(file);
		this.initCategories();
	}

	public final String CATEGORY_GENERAL = "General";
	public final String CATEGORY_MOBS = "Mob options";
	public final String CATEGORY_PROPERTIES = "Block and item properties";
	public final String CATEGORY_GAMESETTINGS = "Force game options";
	public final String CATEGORY_CREATIVETABS = "Creative tabs";
	public final String CATEGORY_HUNGER = "Hunger options";
	public final String CATEGORY_DISABLING = "Disabling";
	public final String CATEGORY_CHAT = "Chat options";

	public String forceDifficulty = "FALSE";
	public boolean noAchievements;
	public boolean noDebug;
	public int maxRenderDistance = 16;
	public boolean tntDropItems = true;
	public boolean logStuff;
	public int stackSizeDivider = 0;
	public boolean allBlocksUnbreakable;
	public int durabilityDivider = 1;
	public int clearRecipes = 0;
	public boolean infiniteDurability;
	public float hardnessMultiplier = 1;
	public boolean disableAnvil;
	public float starvationDamage = 1;
	public boolean noPlantGrowth;
	public boolean noHungerRegen;
	public int minHardness = 0;
	public float explosionSizeMultiplier = 1;
	public boolean removeAllCommands;
	public boolean keepTTCoreBug;
	public int flammabilityMultiplier = 1;
	public boolean removeBlockHighlight = false;
	public float exhaustionMultiplier = 1;
	public int minMessageLength = 1;
	public String defaultChatText;
	public boolean allowCheats = true;
	public long defaultSeed = 0;
	public int boltLivingTimeMultiplier = 1;
	public String displayTitle = "";

	/**
	 * Sets category comments.
	 */

	private void initCategories() {
		this.config.setCategoryComment(this.CATEGORY_GENERAL, "General options.");
		this.config.setCategoryComment(this.CATEGORY_MOBS, "Mob options.");
		this.config.setCategoryComment(this.CATEGORY_PROPERTIES, "Configure block and item properties.");
		this.config.setCategoryComment(this.CATEGORY_GAMESETTINGS, "Force game options.");
		this.config.setCategoryComment(this.CATEGORY_CREATIVETABS, "Disable or enable creative tabs.");
		this.config.setCategoryComment(this.CATEGORY_HUNGER, "Modify hunger options. REQUIRES APPLE CORE!");
		this.config.setCategoryComment(this.CATEGORY_DISABLING, "Disabling of various things.");
		this.config.setCategoryComment(this.CATEGORY_CHAT, "Chat options");
	}

	/**
	 * Reads the config.
	 */

	@Override
	public void loadConfig() {
		this.forceDifficulty = this.config.getString("forceDifficulty", this.CATEGORY_GAMESETTINGS, "FALSE", "Forces the specified difficulty. Allows for HARDCORE, HARD, NORMAL, EASY, PEACEFUL or FALSE. Set to FALSE to disable.");
		this.noAchievements = this.config.getBoolean("noAchievements", this.CATEGORY_GENERAL, false, "Disables achievements.");
		this.noDebug = this.config.getBoolean("noDebug", this.CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		this.maxRenderDistance = this.config.getInt("maxRenderDistance", this.CATEGORY_GAMESETTINGS, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		this.tntDropItems = this.config.getBoolean("tntDropItems", this.CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		this.logStuff = this.config.getBoolean("logStuff", this.CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		this.stackSizeDivider = this.config.getInt("stackSizeDivider", this.CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		this.allBlocksUnbreakable = this.config.getBoolean("allBlocksUnbreakable", this.CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		this.durabilityDivider = this.config.getInt("durabilityDivider", this.CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		this.clearRecipes = this.config.getInt("clearRecipes", this.CATEGORY_GENERAL, 0, 0, 2, "Clears Vanilla recipes if 1, clears all recipes if 2. Set to 0 to disable. Clearing all recipes will not work if any of Reika's mods are loaded.");
		this.infiniteDurability = this.config.getBoolean("infiniteDurability", this.CATEGORY_PROPERTIES, false, "Makes all items have infinite durability. Overrides \"durabilityDivider\".");
		this.hardnessMultiplier = this.config.getFloat("hardnessMultiplier", this.CATEGORY_PROPERTIES, 1, 1, 100, "Multiplies all blocks hardness by the specified number. Set to 1.0 to disable.");
		this.disableAnvil = this.config.getBoolean("disableAnvil", this.CATEGORY_GENERAL, false, "Disables the Vanilla anvil.");
		this.starvationDamage = this.config.getFloat("starvationDamage", this.CATEGORY_HUNGER, 1, 0, 20, "Modifies the starvation damage.");
		this.noPlantGrowth = this.config.getBoolean("noPlantGrowth", this.CATEGORY_HUNGER, false, "Disables plant growth.");
		this.noHungerRegen = this.config.getBoolean("noHungerRegen", this.CATEGORY_HUNGER, false, "Disables hunger regen.");
		this.minHardness = this.config.getInt("minHardness", this.CATEGORY_PROPERTIES, 0, 0, 1080, "Sets the minimum block hardness.");
		this.explosionSizeMultiplier = this.config.getFloat("explosionSizeMultiplier", this.CATEGORY_GENERAL, 1, 0, 1080, "Multiplies the size of all explosions by the specified amount.");
		this.removeAllCommands = this.config.getBoolean("removeAllCommands", this.CATEGORY_GENERAL, false, "Removes all commands, except commands made with SquidUtils' scripting system. Other mods can blacklist their commands from removal.");
		this.keepTTCoreBug = this.config.getBoolean("keepTTCoreBug", this.CATEGORY_GENERAL, false, "Keeps a ttCore/SquidUtils interaction bug launching fireworks whenever the player opens his inventory.");
		this.removeBlockHighlight = this.config.getBoolean("removeBlockHighlight", this.CATEGORY_GENERAL, false, "Removes the box around the block the player is pointing at.");
		this.boltLivingTimeMultiplier = this.config.getInt("boltLivingTimeMultiplier", this.CATEGORY_GENERAL, 1, 0, 200, "Multiplies the lightning bolt living time by the specified amount.");
		this.displayTitle = this.config.getString("displayTitle", this.CATEGORY_GENERAL, "", "Overrides the title of the game display.");
		this.exhaustionMultiplier = this.config.getFloat("exhaustionMultiplier", this.CATEGORY_HUNGER, 1, 0, 1080, "The amount of exhaustion applied to the player will be multiplied by this amount.");
		this.defaultChatText = this.config.getString("defaultChatText", this.CATEGORY_CHAT, "", "");
		this.allowCheats = this.config.getBoolean("allowCheats", this.CATEGORY_GAMESETTINGS, true, "Set to false to force cheats to be disabled.");
		Hooks.NETHER_PORTALS = this.config.getBoolean("netherPortalsAllowed", this.CATEGORY_GENERAL, true, "Set to false to disable nether portals.");
		SquidUtils.COMMON.setDebugMode(this.config.getBoolean("debug", this.CATEGORY_GENERAL, false, "Enables debug mode."));

		this.minMessageLength = this.config.getInt("minMessageLength", this.CATEGORY_CHAT, 1, 0, 32, "");
		for (String s: this.config.getStringList("allowedChars", this.CATEGORY_CHAT, new String[] {}, "")) {
			SquidUtils.COMMON.addAllowedChar(s.charAt(0));
		}

		String defaultSeed = this.config.getString("defaultSeed", this.CATEGORY_GENERAL, "0", "Forces the world seed to be the specified value.");
		try {
			this.defaultSeed = Long.parseLong(defaultSeed);
		} catch (NumberFormatException e) {
			this.defaultSeed = defaultSeed.hashCode();
		}

		try {
			IdentityHashMap<Block, Boolean> carriable = ReflectionHelper.getPrivateStaticValue(EntityEnderman.class, "carriable");
			String[] c = Utils.newBlockNameArray(carriable.keySet());
			carriable.clear();
			for (String s: this.config.getStringList("carriableBlocks", this.CATEGORY_GENERAL, c, "The blocks that endermen can steal.")) {
				carriable.put(Block.getBlockFromName(s), true);
			}
		} catch (ReflectiveOperationException e1) {
			SquidAPI.log.catching(e1);
		}
	}
}