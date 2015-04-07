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
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.asm.Hooks;

public class BlockConfigHandler extends ConfigHandler {

	public static final BlockConfigHandler INSTANCE = new BlockConfigHandler(new SquidAPIFile("./config/SquidUtils/Blocks.cfg"));

	private BlockConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (Object object: Block.blockRegistry) {
			if (object != null && object != Blocks.air && MiscLib.getBlacklister(object) == null) {
				String name = Block.blockRegistry.getNameForObject(object);
				Block block = (Block) object;
				block.setHardness((float) this.config.get(name, "hardness", block.blockHardness).getDouble());
				block.setResistance((float) this.config.get(name, "resistance", block.blockResistance).getDouble());
				if (MiscLib.CLIENT) {
					block.setBlockTextureName(this.config.get(name, "texture", Utils.ensureNotNull(block.textureName)).getString());
					if (block.getCreativeTabToDisplayOn() == null) {
						this.config.get(name, "creativeTab", "null").getString();
					}
					else {
						block.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", block.getCreativeTabToDisplayOn().getTabLabel()).getString()));
					}
				}
				block.slipperiness = (float) this.config.get(name, "slipperiness", block.slipperiness).getDouble();
				block.setLightLevel((float) this.config.get(name, "lightLevel", block.getLightValue() / 15F).getDouble());
				Blocks.fire.setFireInfo(block, this.config.get(name, "fireEncouragement", Blocks.fire.getEncouragement(block)).getInt(), this.config.get(name, "flammability", Blocks.fire.getFlammability(block)).getInt());
				if (block instanceof BlockFalling) {
					if (this.config.get(name, "physics", true).getBoolean()) {
						Hooks.PHYSICS.add(block);
					}
				}
			}
		}
	}
}