/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Configuration;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.config.GeneralConfigHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class Hooks {

	private static final Configuration config = new Configuration(new SquidAPIFile("./config/SquidUtils/ModPermissions.cfg"));

	public static void onSetHardness(Block block, float value) {
		if (block != null) {
			ModContainer mod = Utils.getCurrentMod();
			if (mod == null) {
				mod = Loader.instance().getMinecraftModContainer();
			}
			String name = Block.blockRegistry.getNameForObject(block);
			String modid = mod.getModId();
			boolean self = config.get(modid, "allowSetHardness:self", true).getBoolean();
			boolean all = config.get(modid, "allowSetHardness:all", true).getBoolean();
			if ((self && (name == null || name.startsWith(modid))) || all) {
				block.blockHardness = value;
				if (block.blockResistance < value * 5.0F) {
					block.blockResistance = value * 5.0F;
		        }
			}
		}
	}

	public static void onSetResistance(Block block, float value) {
		if (block != null) {
			ModContainer mod = Utils.getCurrentMod();
			if (mod == null) {
				mod = Loader.instance().getMinecraftModContainer();
			}
			String name = Block.blockRegistry.getNameForObject(block);
			String modid = mod.getModId();
			boolean self = config.get(modid, "allowSetResistance:self", true).getBoolean();
			boolean all = config.get(modid, "allowSetResistance:all", true).getBoolean();
			if ((self && (name == null || name.startsWith(modid))) || all) {
				block.blockResistance = value * 3.0F;
			}
		}
	}

	public static void onSetLightLevel(Block block, float value) {
		if (block != null) {
			ModContainer mod = Utils.getCurrentMod();
			if (mod == null) {
				mod = Loader.instance().getMinecraftModContainer();
			}
			String name = Block.blockRegistry.getNameForObject(block);
			String modid = mod.getModId();
			boolean self = config.get(modid, "allowSetLightLevel:self", true).getBoolean();
			boolean all = config.get(modid, "allowSetLightLevel:all", true).getBoolean();
			if ((self && (name == null || name.startsWith(modid))) || all) {
				block.lightValue = (int) (value * 15.0F);
			}
		}
	}

	public static void onEntityCollideWithPortal(Entity entity) {
		if (GeneralConfigHandler.SETTINGS.getBoolean("netherPortalsAllowed") && entity.ridingEntity == null && entity.riddenByEntity == null) {
			entity.setInPortal();
        }
	}

	public static void save() {
		if (config.hasChanged()) {
			config.save();
		}
	}
}