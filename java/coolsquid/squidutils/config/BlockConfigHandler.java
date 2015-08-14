/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidutils.SquidUtils;

public class BlockConfigHandler extends ConfigHandler {

	public static final ConfigHandler INSTANCE = new BlockConfigHandler(new File("./config/SquidUtils/Blocks.cfg"));
	private static final double DEFAULT_DOUBLE = -1;
	private static final int DEFAULT_INT = -1;

	private BlockConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Object object: Block.blockRegistry.getKeys()) {
			try {
				if (object != null && !object.equals("minecraft:air") && MiscLib.getBlacklister(object) == null) {
					String name = (String) object;
					Block block = Block.getBlockFromName(name);
					if (SquidUtils.COMMON.isDebugMode()) {
						SquidUtils.log.info(name + " (" + block.getClass().getName() + ')');
					}
					block.blockHardness = (float) this.config.get(name, "hardness", block.blockHardness).getDouble();
					block.blockResistance = (float) this.config.get(name, "resistance", block.blockResistance).getDouble();
					if (MiscLib.CLIENT) {
						String texture2 = this.config.get(name, "texture", "default").getString();
						if (!"default".equals(texture2)) {
							block.setBlockTextureName(texture2);
						}
						String tab = this.config.get(name, "creativeTab", "default").getString();
						if (!"default".equals(tab))
							block.setCreativeTab(StringParser.parseCreativeTab(tab));
					}
					double slipperiness = this.config.get(name, "slipperiness", DEFAULT_DOUBLE).getDouble();
					if (slipperiness != DEFAULT_DOUBLE)
						block.slipperiness = (float) slipperiness;
					int lightValue = this.config.get(name, "lightLevel", DEFAULT_INT).getInt();
					if (lightValue != DEFAULT_INT)
						block.lightValue = lightValue;
					try {
						block.stepSound = SquidUtils.API.getSoundTypes().get(this.config.get(name, "stepSound", SquidUtils.API.getSoundTypes().getName(block.stepSound)).getString());
					} catch (Throwable t) {

					}
					Blocks.fire.setFireInfo(block, this.config.get(name, "fireEncouragement", Blocks.fire.getEncouragement(block)).getInt(), this.config.get(name, "flammability", Blocks.fire.getFlammability(block)).getInt());
					if (block instanceof BlockFalling) {
						if (this.config.get(name, "physics", true).getBoolean()) {
							SquidUtils.COMMON.activatePhysicsForBlock((BlockFalling) block);
						}
					}
					if (SquidUtils.API.getMaterials().containsValue(block.blockMaterial)) {
						String material = SquidUtils.API.getMaterials().getName(block.blockMaterial);
						block.blockHardness *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "hardnessMultiplier", 1).getDouble();
						block.blockResistance *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "resistanceMultiplier", 1).getDouble();
						block.slipperiness *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "slipperinessMultiplier", 1).getDouble();
						block.lightValue *= BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "lightLevelMultiplier", 1).getInt();
						Blocks.fire.setFireInfo(block, BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "fireEncouragementMultiplier", 1).getInt() * Blocks.fire.getEncouragement(block), BlockMaterialConfigHandler.INSTANCE.getConfig().get(name, "flammabilityMultiplier", 0).getInt() * Blocks.fire.getFlammability(block));
					}
				}
			} catch (Throwable t) {
				SquidUtils.log.catching(t);
			}
		}
		if (BlockMaterialConfigHandler.INSTANCE.getConfig().hasChanged()) {
			BlockMaterialConfigHandler.INSTANCE.getConfig().save();
		}
	}
}