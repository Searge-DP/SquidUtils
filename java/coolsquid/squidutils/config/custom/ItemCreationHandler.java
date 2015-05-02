/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidutils.config.custom.ItemCreationHandler.ItemContainer;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemCreationHandler extends CustomContentHandler<ItemContainer> {

	public static final ItemCreationHandler INSTANCE = new ItemCreationHandler();

	public ItemCreationHandler() {
		super("items", ItemContainer.class);
	}

	@Override
	protected void handle(ItemContainer fromJson) {
		GameRegistry.registerItem(fromJson.item, fromJson.name);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ItemDeserializer(), ItemContainer.class);
	}

	public static class ItemDeserializer implements JsonDeserializer<ItemContainer> {

		@Override
		public ItemContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			Item item = null;
			if (o.has("type")) {
				String type = o.get("type").getAsString();
				if (type.equals("itemFood")) {
					item = new ItemFood(o.get("healAmount").getAsInt(), o.get("saturation").getAsFloat(), o.has("wolfMeat") ? o.get("wolfMeat").getAsBoolean() : false);
					ItemFood food = (ItemFood) item;
					if (o.has("alwaysEdible")) {
						food.alwaysEdible = o.get("alwaysEdible").getAsBoolean();
					}
					if (o.has("eatingTime")) {
						food.itemUseDuration = o.get("eatingTime").getAsInt();
					}
					if (o.has("potionId")) {
						food.setPotionEffect(o.get("potionId").getAsInt(), o.get("duration").getAsInt(), o.get("amplifier").getAsInt(), o.has("potionEffectProbability") ? o.get("potionEffectProbability").getAsFloat() : 1);
					}
				}
			}
			else {
				item = new Item();
			}
			if (o.has("maxStackSize")) {
				item.maxStackSize = o.get("maxStackSize").getAsInt();
			}
			if (o.has("maxDamage")) {
				item.maxDamage = o.get("maxDamage").getAsInt();
			}
			if (o.has("texture")) {
				item.setTextureName(o.get("texture").getAsString());
			}
			else {
				item.setTextureName(name);
			}
			item.setUnlocalizedName(name);
			return new ItemContainer(item, name);
		}
	}

	public static class ItemContainer {

		public final Item item;
		public final String name;

		public ItemContainer(Item item, String name) {
			this.item = item;
			this.name = name;
		}
	}
}