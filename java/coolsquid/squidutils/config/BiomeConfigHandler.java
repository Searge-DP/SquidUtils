/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;

import com.google.common.collect.Lists;

import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.util.io.SquidAPIFile;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class BiomeConfigHandler extends ConfigHandler {

	public static final BiomeConfigHandler INSTANCE = new BiomeConfigHandler(new SquidAPIFile("./config/SquidUtils/Biomes.cfg"));

	private BiomeConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (BiomeGenBase biome: BiomeGenBase.getBiomeGenArray()) {
			if (biome != null) {
				String name = biome.biomeName;
				if (name == null) {
					name = biome.biomeID + "";
				}
				biome.setBiomeName(this.config.get(name, "name", name).getString());
				biome.topBlock = Block.getBlockFromName(this.config.get(name, "topBlock", Block.blockRegistry.getNameForObject(biome.topBlock)).getString());
				biome.fillerBlock = Block.getBlockFromName(this.config.get(name, "fillerBlock", Block.blockRegistry.getNameForObject(biome.fillerBlock)).getString());
				biome.rootHeight = (float) this.config.get(name, "rootHeight", biome.rootHeight).getDouble();
				biome.heightVariation = (float) this.config.get(name, "heightVariation", biome.heightVariation).getDouble();
				biome.temperature = (float) this.config.get(name, "temperature", biome.temperature).getDouble();
				biome.color = this.config.get(name, "color", biome.color).getInt();
				biome.rainfall = (float) this.config.get(name, "rainfall", biome.rainfall).getDouble();
				try {
					@SuppressWarnings("unchecked")
					List<FlowerEntry> flowers = (List<FlowerEntry>) ReflectionHelper.findField(BiomeGenBase.class, "flowers").get(biome);
					List<String> a = Lists.newArrayList();
					for (FlowerEntry flower: flowers) {
						a.add(Block.blockRegistry.getNameForObject(flower.block) + ":" + flower.metadata + ":" + flower.itemWeight);
					}
					flowers.clear();
					for (String flower: this.config.get(name, "flowers", a.toArray(new String[] {})).getStringList()) {
						String[] b = flower.split(":");
						flowers.add(new FlowerEntry(Block.getBlockFromName(b[0] + b[1]), Integer.parseInt(b[2]), Integer.parseInt(b[3])));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}