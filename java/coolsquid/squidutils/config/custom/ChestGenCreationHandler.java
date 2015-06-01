/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChestGenCreationHandler extends CreationHandler<WeightedRandomChestContent> {

	public static final ChestGenCreationHandler INSTANCE = new ChestGenCreationHandler();

	public ChestGenCreationHandler() {
		super("chestgen", WeightedRandomChestContent.class);
	}

	@Override
	protected void handle(WeightedRandomChestContent fromJson) {
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, fromJson);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ChestGenDeserializer(), WeightedRandomChestContent.class);
	}

	public static class ChestGenDeserializer implements JsonDeserializer<WeightedRandomChestContent> {

		@Override
		public WeightedRandomChestContent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			return new WeightedRandomChestContent((Item) Item.itemRegistry.getObject(o.get("item").getAsString()), o.has("itemDamage") ? o.get("itemDamage").getAsInt() : 0, o.get("minimumAmount").getAsInt(), o.get("maximumAmount").getAsInt(), o.get("weight").getAsInt());
		}
	}
}