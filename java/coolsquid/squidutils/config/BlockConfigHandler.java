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
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.asm.Hooks;

public class BlockConfigHandler extends ConfigHandler {

	public static final BlockConfigHandler INSTANCE = new BlockConfigHandler(new SquidAPIFile("./config/SquidUtils/Blocks.cfg"));

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
						SquidUtils.instance().info(name, '(', block.getClass().getName(), ')');
					}
					block.blockHardness = (float) this.config.get(name, "hardness", block.blockHardness).getDouble();
					block.blockResistance = (float) this.config.get(name, "resistance", block.blockResistance).getDouble();
					if (MiscLib.CLIENT) {
						String texture = StringUtils.ensureNotNull(block.textureName);
						String texture2 = this.config.get(name, "texture", texture).getString();
						if (!texture.equals(texture2)) {
							block.setBlockTextureName(texture2);
						}
						if (block.getCreativeTabToDisplayOn() == null) {
							this.config.get(name, "creativeTab", "null").getString();
						}
						else {
							block.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", block.getCreativeTabToDisplayOn().getTabLabel()).getString()));
						}
					}
					block.slipperiness = (float) this.config.get(name, "slipperiness", block.slipperiness).getDouble();
					block.lightValue = this.config.get(name, "lightLevel", block.lightValue).getInt();
					Blocks.fire.setFireInfo(block, this.config.get(name, "fireEncouragement", Blocks.fire.getEncouragement(block)).getInt(), this.config.get(name, "flammability", Blocks.fire.getFlammability(block)).getInt());
					if (block instanceof BlockFalling) {
						if (this.config.get(name, "physics", true).getBoolean()) {
							Hooks.PHYSICS.add(block);
						}
					}
					if (SquidUtils.API.getMaterials().containsValue(block.blockMaterial)) {
						String material = SquidUtils.API.getMaterials().getName(block.blockMaterial);
						block.blockHardness *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "hardnessMultiplier", 0).getDouble();
						block.blockResistance *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "resistanceMultiplier", 0).getDouble();
						block.slipperiness *= (float) BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "slipperinessMultiplier", 0).getDouble();
						block.lightValue *= BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "lightLevelMultiplier", 0).getInt();
						Blocks.fire.setFireInfo(block, BlockMaterialConfigHandler.INSTANCE.getConfig().get(material, "fireEncouragementMultiplier", 0).getInt() * Blocks.fire.getEncouragement(block), this.config.get(name, "flammabilityMultiplier", 0).getInt() * Blocks.fire.getFlammability(block));
					}
				}
			} catch (Throwable t) {
				SquidUtils.instance().error(object.getClass().getName());
				SquidUtils.instance().error(t);
			}
		}
		if (BlockMaterialConfigHandler.INSTANCE.getConfig().hasChanged()) {
			BlockMaterialConfigHandler.INSTANCE.getConfig().save();
		}
	}
}