/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidapi.block.BlockBase;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.config.custom.BlockCreationHandler.BlockContainer;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockCreationHandler extends CustomContentHandler<BlockContainer> {

	public static final BlockCreationHandler INSTANCE = new BlockCreationHandler();
	private static final Map<String, Factory<? extends Block>> factories = Maps.newHashMap();

	public BlockCreationHandler() {
		super("blocks", BlockContainer.class);
	}

	public static void registerFactory(String type, Factory<?> factory) {
		factories.put(type.toUpperCase(), factory);
	}

	@Override
	protected void handle(BlockContainer fromJson) {
		GameRegistry.registerBlock(fromJson.block, fromJson.name);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new BlockDeserializer(), BlockContainer.class);

		registerFactory("fluid", new BlockFluidFactory());
		registerFactory("basic", new BlockBasicFactory());
		registerFactory("crops", new SimpleFactory<BlockCrops>(BlockCrops.class));
		registerFactory("falling", new SimpleFactory<BlockFalling>(BlockFalling.class));
	}

	public static class BlockDeserializer implements JsonDeserializer<BlockContainer> {

		@Override
		public BlockContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			Block block = factories.get(o.get("type").getAsString().toUpperCase()).newInstance(o, context);
			block.setBlockName(name);
			block.setBlockTextureName(o.has("texture") ? o.get("texture").getAsString() : name);
			if (o.has("hardness")) {
				block.setHardness(o.get("hardness").getAsFloat());
			}
			if (o.has("resistance")) {
				block.setResistance(o.get("hardness").getAsFloat());
			}
			if (o.has("lightLevel")) {
				block.setLightLevel(o.get("lightLevel").getAsFloat());
			}
			if (o.has("harvestLevel") && o.has("toolClass")) {
				block.setHarvestLevel(o.get("toolClass").getAsString(), o.get("harvestLevel").getAsInt());
			}
			if (o.has("creativeTab")) {
				block.setCreativeTab(SquidUtils.COMMON.getCreativeTabs().get(o.get("creativeTab").getAsString()));
			}
			return new BlockContainer(block, name);
		}
	}

	public static class BlockContainer {

		public final Block block;
		public final String name;

		public BlockContainer(Block block, String name) {
			this.block = block;
			this.name = name;
		}
	}

	public static interface Factory<E extends Block> {
		public E newInstance(JsonObject o, JsonDeserializationContext context);
	}

	public static class SimpleFactory<E extends Block> implements Factory<E> {

		private final Class<E> type;

		public SimpleFactory(Class<E> type) {
			this.type = type;
		}

		@Override
		public E newInstance(JsonObject o, JsonDeserializationContext context) {
			return context.deserialize(o, this.type);
		}
	}

	public static class BlockFluidFactory implements Factory<BlockFluidClassic> {

		@Override
		public BlockFluidClassic newInstance(JsonObject o, JsonDeserializationContext context) {
			Fluid fluid = new Fluid(o.get("name").getAsString());
			if (o.has("density")) {
				fluid.setDensity(o.get("density").getAsInt());
			}
			if (o.has("gaseous")) {
				fluid.setGaseous(o.get("gaseous").getAsBoolean());
			}
			if (o.has("luminosity")) {
				fluid.setLuminosity(o.get("luminosity").getAsInt());
			}
			if (o.has("rarity")) {
				fluid.setRarity(EnumRarity.valueOf(o.get("rarity").getAsString()));
			}
			if (o.has("temperature")) {
				fluid.setTemperature(o.get("temperature").getAsInt());
			}
			if (o.has("viscosity")) {
				fluid.setViscosity(o.get("viscosity").getAsInt());
			}
			FluidRegistry.registerFluid(fluid);
			BlockFluidClassic block;
			if (o.has("effectId")) {
				int id = o.get("effectId").getAsInt();
				int duration = o.get("effectDuration").getAsInt();
				int amplifier = o.has("effectAmplifier") ? o.get("effectAmplifier").getAsInt() : 0;
				block = new BlockFluidClassic(fluid, SquidUtils.API.getMaterials().get(o.get("material").getAsString())) {

					@Override
					public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
						if (entity instanceof EntityLivingBase) {
							((EntityLivingBase) entity).addPotionEffect(new PotionEffect(id, duration, amplifier));
						}
					}
				};
			}
			else {
				block = new BlockFluidClassic(fluid, SquidUtils.API.getMaterials().get(o.get("material").getAsString()));
			}
			fluid.setBlock(block);
			return block;
		}
	}

	public static class BlockBasicFactory implements Factory<BlockBase> {

		@Override
		public BlockBase newInstance(JsonObject o, JsonDeserializationContext context) {
			return new BlockBase(SquidUtils.API.getMaterials().get(o.get("material").getAsString()));
		}
	}
}