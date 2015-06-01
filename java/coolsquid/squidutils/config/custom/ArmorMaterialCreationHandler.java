/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ArmorMaterialCreationHandler extends CreationHandler<ArmorMaterial> {

	public ArmorMaterialCreationHandler() {
		super("armormaterials", ArmorMaterial.class);
	}

	@Override
	protected void handle(ArmorMaterial fromJson) {

	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ArmorMaterialDeserializer(), ArmorMaterial.class);
	}

	public static class ArmorMaterialDeserializer implements JsonDeserializer<ArmorMaterial> {

		@Override
		public ArmorMaterial deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			return EnumHelper.addArmorMaterial(o.get("name").getAsString(), o.get("durability").getAsInt(), context.deserialize(o.get("reductionAmounts"), int[].class), o.get("enchantability").getAsInt());
		}
	}
}