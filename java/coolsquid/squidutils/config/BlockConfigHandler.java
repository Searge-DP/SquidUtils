/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import coolsquid.squidapi.util.StringParser;

public class BlockConfigHandler {

	public static final BlockConfigHandler INSTANCE = new BlockConfigHandler();

	private BlockConfigHandler() {
		
	}

	private File configFile;

	private Configuration config;

	public final void init(File file) {
		this.configFile = file;
		this.createConfig();
		this.readConfig();
	}

	private final void createConfig() {
		if (this.config == null)
			this.config = new Configuration(this.configFile);
	}

	private final void readConfig() {
		for (Object object: Block.blockRegistry) {
			if (object != null && object != Blocks.air) {
				String name = Block.blockRegistry.getNameForObject(object);
				Block block = (Block) object;
				block.setHardness((float) this.config.get(name, "hardness", block.getBlockHardness(null, 0, 0, 0)).getDouble());
				block.setResistance((float) this.config.get(name, "resistance", block.getExplosionResistance(null) * 5.0F).getDouble());
				//block.setBlockTextureName(this.config.getString("texture", name, block.getTextureName(), ""));
				if (block.getCreativeTabToDisplayOn() == null) {
					this.config.get(name, "creativeTab", "null").getString();
				}
				else {
					block.setCreativeTab(StringParser.parseCreativeTab(this.config.get(name, "creativeTab", block.getCreativeTabToDisplayOn().getTabLabel()).getString()));
				}
				block.slipperiness = (float) this.config.get(name, "slipperiness", block.slipperiness).getDouble();
				block.setLightLevel((float) this.config.get(name, "lightLevel", block.getLightValue()).getDouble());
				Blocks.fire.setFireInfo(block, this.config.get(name, "fireEncouragement", Blocks.fire.getEncouragement(block)).getInt(), this.config.get(name, "flammability", Blocks.fire.getFlammability(block)).getInt());
			}
		}

		if (this.config.hasChanged()) {
			this.config.save();
		}
	}
}