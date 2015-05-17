/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.compat.minetweaker;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import coolsquid.squidutils.SquidUtils;

@ZenClass("mods.squidutils.Blocks")
public class BlockUtils {

	@ZenMethod
	public static void setHardness(String name, float value) {
		Block.getBlockFromName(name).blockHardness = value;
	}

	@ZenMethod
	public static void setMaterial(String name, String material) {
		Block.getBlockFromName(name).blockMaterial = SquidUtils.API.getMaterials().get(material);
	}

	@ZenMethod
	public static void setResistance(String name, float value) {
		Block.getBlockFromName(name).blockResistance = value;
	}

	@ZenMethod
	public static void setCreativeTab(String name, String tab) {
		Block.getBlockFromName(name).setCreativeTab(SquidUtils.COMMON.getCreativeTabs().get(tab));
	}

	@ZenMethod
	public static void setFlammibility(String name, int flammibility, int encouragement) {
		Blocks.fire.setFireInfo(Block.getBlockFromName(name), encouragement, flammibility);
	}
}