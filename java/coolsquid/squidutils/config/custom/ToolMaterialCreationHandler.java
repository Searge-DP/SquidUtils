/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ToolMaterialCreationHandler extends CreationHandler<ToolMaterial> {

	public static final ToolMaterialCreationHandler INSTANCE = new ToolMaterialCreationHandler();

	public ToolMaterialCreationHandler() {
		super("toolmaterials", ToolMaterial[].class);
	}

	@Override
	protected void handle(ToolMaterial fromJson) {

	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ToolMaterialDeserializer(), ToolMaterial.class);
	}

	public static class ToolMaterialDeserializer implements JsonDeserializer<ToolMaterial> {

		@Override
		public ToolMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			return EnumHelper.addToolMaterial(name, o.get("harvestLevel").getAsInt(), o.get("durability").getAsInt(), o.get("effifiency").getAsInt(), o.get("damage").getAsInt(), o.get("enchantability").getAsInt());
		}
	}
}