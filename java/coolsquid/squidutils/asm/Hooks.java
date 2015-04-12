/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import com.google.common.collect.Sets;

import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.io.SquidAPIFile;
import cpw.mods.fml.common.ModContainer;

public class Hooks {

	private static final Configuration config = new Configuration(new SquidAPIFile("./config/SquidUtils/ModPermissions.cfg"));
	public static final Set<Block> PHYSICS = Sets.newHashSet();
	public static boolean NETHER_PORTALS = true;

	public static void onSetHardness(Block block, float value) {
		if (block != null) {
			ModContainer mod = Utils.getCurrentMod();
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
			String name = Block.blockRegistry.getNameForObject(block);
			String modid = mod.getModId();
			boolean self = config.get(modid, "allowSetLightLevel:self", true).getBoolean();
			boolean all = config.get(modid, "allowSetLightLevel:all", true).getBoolean();
			if ((self && (name == null || name.startsWith(modid))) || all) {
				block.lightValue = (int) (value * 15.0F);
			}
		}
	}

	public static void onBlockFallingUpdate(BlockFalling block, World world, int x, int y, int z) {
		if (!world.isRemote && PHYSICS.contains(block)) {
            block.func_149830_m(world, x, y, z);
        }
	}

	public static void onEntityCollideWithPortal(Entity entity) {
		if (NETHER_PORTALS && entity.ridingEntity == null && entity.riddenByEntity == null) {
			entity.setInPortal();
        }
	}

	public static void save() {
		if (config.hasChanged()) {
			config.save();
		}
	}
}