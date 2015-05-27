/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidutils.SquidUtils;

public class BlockMaterialCreationHandler extends CustomContentHandler<Material> {

	public static final BlockMaterialCreationHandler INSTANCE = new BlockMaterialCreationHandler();

	public BlockMaterialCreationHandler() {
		super("materials", Material.class);
	}

	@Override
	protected void handle(Material fromJson) {
		SquidUtils.API.registerMaterial(fromJson.toString(), fromJson);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new MaterialDeserializer(), Material.class);
	}

	public static class MaterialDeserializer implements JsonDeserializer<Material> {

		@Override
		public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			Material material = new Material(MapColor.getMapColorForBlockColored(o.get("mapColor").getAsInt())) {
				@Override
				public String toString() {
					return name;
				}
			};
			material.canBurn = o.get("burnable").getAsBoolean();
			material.isAdventureModeExempt = o.get("adventureModeExempt").getAsBoolean();
			material.isTranslucent = o.get("translucent").getAsBoolean();
			material.mobilityFlag = o.get("mobility").getAsInt();
			material.replaceable = o.get("replaceable").getAsBoolean();
			material.requiresNoTool = o.get("requiresNoTool").getAsBoolean();
			return material;
		}
	}
}