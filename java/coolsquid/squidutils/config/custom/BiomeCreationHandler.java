/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidutils.config.custom.BiomeCreationHandler.Biome;

public class BiomeCreationHandler extends CreationHandler<Biome> {

	public static final BiomeCreationHandler INSTANCE = new BiomeCreationHandler();

	public BiomeCreationHandler() {
		super("biomes", Biome[].class);
	}

	@Override
	protected void handle(Biome fromJson) {
		BiomeManager.addBiome(BiomeType.getType(fromJson.type), new BiomeEntry(fromJson, fromJson.weight));
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new BiomeDeserializer(), Biome.class);
	}

	public static class BiomeDeserializer implements JsonDeserializer<Biome> {

		@Override
		public Biome deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject object = json.getAsJsonObject();
			Biome biome = new Biome(object.get("id").getAsInt()) {};
			biome.biomeName = object.get("name").getAsString();
			biome.color = object.get("color").getAsInt();
			biome.topBlock = Block.getBlockFromName(object.get("topBlock").getAsString());
			biome.fillerBlock = Block.getBlockFromName(object.get("fillerBlock").getAsString());
			biome.temperature = object.get("temperature").getAsFloat();
			biome.rainfall = object.get("rainfall").getAsFloat();
			biome.rootHeight = object.get("heightVariation").getAsFloat();
			biome.heightVariation = object.get("heightVariation").getAsFloat();
			biome.waterColorMultiplier = object.get("waterColorMultiplier").getAsInt();
			biome.weight = object.get("weight").getAsInt();
			biome.type = object.get("type").getAsString();
			return biome;
		}
	}

	public static class Biome extends BiomeGenBase {

		public int weight;
		public String type;

		public Biome(int id) {
			super(id);
		}
	}
}