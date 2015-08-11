/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import net.minecraft.block.BlockFalling;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.objectweb.asm.Type;

import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class Hooks {

	private static final String NAME = Type.getInternalName(Hooks.class);

	public static boolean NETHER_PORTALS = true;

	public static void onBlockFallingUpdate(BlockFalling block, World world, int x, int y, int z) {
		if (!world.isRemote && SquidUtils.COMMON.hasPhysics(block)) {
			block.func_149830_m(world, x, y, z);
		}
	}

	public static void onEntityCollideWithPortal(Entity entity) {
		if (NETHER_PORTALS && entity.ridingEntity == null && entity.riddenByEntity == null) {
			entity.setInPortal();
		}
	}

	public static boolean isAllowedChar(char c) {
		return SquidUtils.COMMON.isAllowedChar(c) || (c > 31 && c != 167 && c != 127);
	}

	@SideOnly(Side.CLIENT)
	public static boolean hasSearchBar(CreativeTabs c) {
		return c == CreativeTabs.tabAllSearch || SquidUtils.COMMON.hasSearchBar(c);
	}

	public static String getInternalName() {
		return NAME;
	}
}