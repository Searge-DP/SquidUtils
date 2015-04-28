/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.IdentityHashMap;

import javax.swing.JOptionPane;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.reflection.ReflectionHelper;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.collect.SquidAPIProperties;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.asm.Hooks;

public class GeneralConfigHandler extends ConfigHandler {

	public static final GeneralConfigHandler INSTANCE = new GeneralConfigHandler(new SquidAPIFile("./config/SquidUtils/SquidUtils.cfg"));
	public static final SquidAPIProperties SETTINGS = new SquidAPIProperties();

	private GeneralConfigHandler(File file) {
		super(file);
		this.initCategories();
	}

	public final String CATEGORY_GENERAL = "General";
	private final String CATEGORY_MOBS = "Mob options";
	private final String CATEGORY_PROPERTIES = "Block and item properties";
	private final String CATEGORY_GAMESETTINGS = "Force game options";
	private final String CATEGORY_CREATIVETABS = "Creative tabs";
	private final String CATEGORY_HUNGER = "Hunger options";
	private final String CATEGORY_DISABLING = "Disabling";
	private final String CATEGORY_CHAT = "Chat options";

	public String forceDifficulty = "FALSE";
	public boolean noTNT;
	public boolean noAchievements;
	public boolean noWitherBoss;
	public int potionStacks = 1;
	public boolean chainRecipes;
	public boolean noDebug;
	public int pearlStack = 16;
	public int maxRenderDistance = 16;
	public boolean tntDropItems = true;
	public boolean villagerProtection;
	public boolean logStuff;
	public int stackSizeDivider = 0;
	public boolean allBlocksUnbreakable;
	public int durabilityDivider = 1;
	public int clearRecipes = 0;
	public boolean tabVanilla = true;
	public boolean infiniteDurability;
	public float hardnessMultiplier = 1;
	public boolean disableAnvil;
	public boolean disableTeleportation;
	public boolean disableBonemeal;
	public boolean disableHoes;
	public boolean disableBottleFluidInteraction;
	public float starvationDamage = 1;
	public boolean noPlantGrowth;
	public boolean noHungerRegen;
	public float walkSpeed = 0.1F;
	public float flySpeed = 0.05F;
	public int minHardness = 0;
	public float explosionSizeMultiplier = 1;
	public int worldSize = 0;
	public boolean explodeTNTMinecartsOnCollide;
	public boolean removeAllCommands;
	public boolean keepTTCoreBug;
	public int flammabilityMultiplier = 1;
	public boolean removeBlockHighlight = false;
	public float exhaustionMultiplier = 1;
	public int minMessageLength = 1;
	public String defaultChatText;
	public boolean allowCheats = true;
	public long defaultSeed = 0;

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
		this.noTNT = this.config.getBoolean("noTNT", this.CATEGORY_GENERAL, false, "Stops TNT from exploding.");
		this.noAchievements = this.config.getBoolean("noAchievements", this.CATEGORY_GENERAL, false, "Disables achievements.");
		this.noWitherBoss = this.config.getBoolean("noWitherBoss", this.CATEGORY_GENERAL, false, "Disables the witherboss.");
		this.potionStacks = this.config.getInt("maxPotionStackSize", this.CATEGORY_PROPERTIES, 1, 1, 64, "Sets the max stacksize for potions.");
		this.chainRecipes = this.config.getBoolean("chainRecipes", this.CATEGORY_GENERAL, false, "Makes recipes for all pieces of chain armor.");
		this.noDebug = this.config.getBoolean("noDebug", this.CATEGORY_GENERAL, false, "Makes it impossible to open the debug screen.");
		this.pearlStack = this.config.getInt("maxEnderPearlStackSize", this.CATEGORY_PROPERTIES, 16, 1, 64, "Sets the max stacksize for enderpearls.");
		this.maxRenderDistance = this.config.getInt("maxRenderDistance", this.CATEGORY_GAMESETTINGS, 16, 1, 16, "Sets the max render distance. Set to 16 to disable.");
		this.tntDropItems = this.config.getBoolean("tntDropItems", this.CATEGORY_GENERAL, true, "Should TNT drop items when removed? Only applies if \"noTNT\" is true.");
		this.villagerProtection = this.config.getBoolean("villagerProtection", this.CATEGORY_MOBS, false, "Makes villagers unhurtable.");
		this.logStuff = this.config.getBoolean("logStuff", this.CATEGORY_GENERAL, false, "Logs all blocks broken and all entity deaths.");
		this.stackSizeDivider = this.config.getInt("stackSizeDivider", this.CATEGORY_PROPERTIES, 0, 0, 64, "Sets the max stack size for all items. Set to 0 to disable.");
		this.allBlocksUnbreakable = this.config.getBoolean("allBlocksUnbreakable", this.CATEGORY_PROPERTIES, false, "Makes all blocks unbreakable.");
		this.durabilityDivider = this.config.getInt("durabilityDivider", this.CATEGORY_PROPERTIES, 1, 1, 1080, "All tools and armors durability will be divided by this.");
		this.clearRecipes = this.config.getInt("clearRecipes", this.CATEGORY_GENERAL, 0, 0, 2, "Clears Vanilla recipes if 1, clears all recipes if 2. Set to 0 to disable. Clearing all recipes will not work if any of Reika's mods are loaded.");
		this.infiniteDurability = this.config.getBoolean("infiniteDurability", this.CATEGORY_PROPERTIES, false, "Makes all items have infinite durability. Overrides \"durabilityDivider\".");
		this.tabVanilla = this.config.getBoolean("tabVanilla", this.CATEGORY_CREATIVETABS, false, "Enables the extra Vanilla stuff creative tab.");
		this.hardnessMultiplier = this.config.getFloat("hardnessMultiplier", this.CATEGORY_PROPERTIES, 1, 1, 100, "Multiplies all blocks hardness by the specified number. Set to 1.0 to disable.");
		this.disableAnvil = this.config.getBoolean("disableAnvil", this.CATEGORY_GENERAL, false, "Disables the Vanilla anvil.");
		this.disableTeleportation = this.config.getBoolean("disableTeleportation", this.CATEGORY_GENERAL, false, "Disables enderman and enderpearl teleportation.");
		this.disableBonemeal = this.config.getBoolean("disableBonemeal", this.CATEGORY_GENERAL, false, "Disables bonemeal.");
		this.disableHoes = this.config.getBoolean("disableHoes", this.CATEGORY_GENERAL, false, "Disables hoes.");
		this.disableBottleFluidInteraction = this.config.getBoolean("disableBottleFluidInteraction", this.CATEGORY_GENERAL, false, "Disables bottles from working with cauldrons.");
		this.starvationDamage = this.config.getFloat("starvationDamage", this.CATEGORY_HUNGER, 1, 0, 20, "Modifies the starvation damage.");
		this.noPlantGrowth = this.config.getBoolean("noPlantGrowth", this.CATEGORY_HUNGER, false, "Disables plant growth.");
		this.noHungerRegen = this.config.getBoolean("noHungerRegen", this.CATEGORY_HUNGER, false, "Disables hunger regen.");
		this.walkSpeed = this.config.getFloat("walkSpeed", this.CATEGORY_GENERAL, 0.1F, 0F, 20F, "Sets the players walk speed.");
		this.flySpeed = this.config.getFloat("flySpeed", this.CATEGORY_GENERAL, 0.05F, 0F, 20F, "Sets the players flying speed.");
		this.minHardness = this.config.getInt("minHardness", this.CATEGORY_PROPERTIES, 0, 0, 1080, "Sets the minimum block hardness.");
		this.explosionSizeMultiplier = this.config.getFloat("explosionSizeMultiplier", this.CATEGORY_GENERAL, 1, 0, 1080, "Multiplies the size of all explosions by the specified amount.");
		this.worldSize = this.config.getInt("worldSize", this.CATEGORY_GENERAL, 0, 0, Integer.MAX_VALUE, "Sets the size of the world. Set to 0 to disable.");
		this.explodeTNTMinecartsOnCollide = this.config.getBoolean("explodeTNTMinecartsOnCollide", this.CATEGORY_GENERAL, false, "Explodes minecarts with TNT whenever they collide with an entity.");
		this.removeAllCommands = this.config.getBoolean("removeAllCommands", this.CATEGORY_GENERAL, false, "Removes all commands, except commands made with SquidUtils' scripting system. Other mods can blacklist their commands from removal.");
		this.keepTTCoreBug = this.config.getBoolean("keepTTCoreBug", this.CATEGORY_GENERAL, false, "Keeps a ttCore/SquidUtils interaction bug launching fireworks whenever the player opens his inventory.");
		this.removeBlockHighlight = this.config.getBoolean("removeBlockHighlight", this.CATEGORY_GENERAL, false, "Removes the box around the block the player is pointing at.");
		SETTINGS.set("boltLivingTimeMultiplier", this.config.getInt("boltLivingTimeMultiplier", this.CATEGORY_GENERAL, 1, 0, 200, "Multiplies the lightning bolt living time by the specified amount."));
		SETTINGS.set("displayTitle", this.config.getString("displayTitle", this.CATEGORY_GENERAL, "", "Overrides the title of the game display."));
		Hooks.NETHER_PORTALS = this.config.getBoolean("netherPortalsAllowed", this.CATEGORY_GENERAL, true, "Set to false to disable nether portals.");
		this.exhaustionMultiplier = this.config.getFloat("exhaustionMultiplier", this.CATEGORY_HUNGER, 1, 0, 1080, "The amount of exhaustion applied to the player will be multiplied by this amount.");
		this.defaultChatText = this.config.getString("defaultChatText", this.CATEGORY_CHAT, "", "");
		this.allowCheats = this.config.getBoolean("allowCheats", this.CATEGORY_GAMESETTINGS, true, "Set to false to force cheats to be disabled.");
		this.defaultSeed = (long) this.config.getFloat("defaultSeed", this.CATEGORY_GENERAL, 0, Float.MIN_VALUE, Float.MAX_VALUE, "Forces the world seed to be the specified number.");
		SquidUtils.COMMON.setDebug(this.config.getBoolean("debug", this.CATEGORY_GENERAL, false, "Enables debug mode."));

		this.minMessageLength = this.config.getInt("minMessageLength", this.CATEGORY_CHAT, 1, 0, 32, "");
		for (String s: this.config.getStringList("allowedChars", this.CATEGORY_CHAT, new String[] {}, "")) {
			Hooks.ALLOWED_CHARS.add(s.charAt(0));
		}

		IdentityHashMap<Block, Boolean> carriable = ReflectionHelper.in(EntityEnderman.class).field("carriable", "carriable").get();
		String[] c = Utils.newBlockNameArray(carriable.keySet());
		carriable.clear();
		for (String s: this.config.getStringList("carriableBlocks", this.CATEGORY_GENERAL, c, "The blocks that endermen can steal.")) {
			carriable.put(Block.getBlockFromName(s), true);
		}

		String password = this.config.getString("password", this.CATEGORY_GENERAL, "", "Sets a password required to launch Minecraft.");
		if (!(password.isEmpty())) {
			try {
				String p = JOptionPane.showInputDialog("password:");
				if (!p.equals(password)) {
					throw new SecurityException("Wrong password.");
				}
			}
			catch (NullPointerException e) {
				throw new SecurityException("Wrong password.");
			}
		}
	}
}