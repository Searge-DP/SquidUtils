/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidutils.config.custom.ItemCreationHandler.ItemContainer;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemCreationHandler extends CreationHandler<ItemContainer> {

	public static final ItemCreationHandler INSTANCE = new ItemCreationHandler();
	private static final Map<String, Factory<? extends Item>> factories = Maps.newHashMap();

	public ItemCreationHandler() {
		super("items", ItemContainer[].class);
	}

	@Override
	protected void handle(ItemContainer fromJson) {
		GameRegistry.registerItem(fromJson.getItem(), fromJson.getName());
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new ItemDeserializer(), ItemContainer.class);

		factories.put("food", new ItemFoodFactory());
		factories.put("basic", new ItemBasicFactory());
		factories.put("armor", new ItemArmorFactory());
		factories.put("pickaxe", new ItemPickaxeFactory());
		factories.put("sword", new ItemSwordFactory());
		factories.put("axe", new ItemAxeFactory());
		factories.put("shovel", new ItemSpadeFactory());
	}

	public static class ItemDeserializer implements JsonDeserializer<ItemContainer> {

		@Override
		public ItemContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			Item item = factories.get(o.get("type").getAsString()).newInstance(o, context);
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

		private final Item item;
		private final String name;

		public ItemContainer(Item item, String name) {
			this.item = item;
			this.name = name;
		}

		public Item getItem() {
			return this.item;
		}

		public String getName() {
			return this.name;
		}
	}
	public static class ItemFoodFactory implements Factory<ItemFood> {

		@Override
		public ItemFood newInstance(JsonObject o, JsonDeserializationContext context) {
			ItemFood food = new ItemFood(o.get("healAmount").getAsInt(), o.get("saturation").getAsFloat(), o.has("wolfMeat") ? o.get("wolfMeat").getAsBoolean() : false);
			if (o.has("alwaysEdible")) {
				food.alwaysEdible = o.get("alwaysEdible").getAsBoolean();
			}
			if (o.has("eatingTime")) {
				food.itemUseDuration = o.get("eatingTime").getAsInt();
			}
			if (o.has("potionId")) {
				food.setPotionEffect(o.get("potionId").getAsInt(), o.get("duration").getAsInt(), o.get("amplifier").getAsInt(), o.has("potionEffectProbability") ? o.get("potionEffectProbability").getAsFloat() : 1);
			}
			return food;
		}
	}

	public static class ItemBasicFactory implements Factory<Item> {

		@Override
		public Item newInstance(JsonObject o, JsonDeserializationContext context) {
			return new Item();
		}
	}

	public static class ItemArmorFactory implements Factory<ItemArmor> {

		@Override
		public ItemArmor newInstance(JsonObject o, JsonDeserializationContext context) {
			ArmorMaterial m = null;
			for (ArmorMaterial material: ArmorMaterial.values()) {
				if (material.toString().equals(o.get("name").getAsString())) {
					m = material;
				}
			}
			return new ItemArmor(m, o.get("renderIndex").getAsInt(), o.get("armorType").getAsInt());
		}
	}

	public static class ItemPickaxeFactory implements Factory<ItemPickaxe> {

		@Override
		public ItemPickaxe newInstance(JsonObject o, JsonDeserializationContext context) {
			return new ItemPickaxe(getToolMaterial(o.get("name").getAsString())) {};
		}
	}

	public static class ItemSpadeFactory implements Factory<ItemSpade> {

		@Override
		public ItemSpade newInstance(JsonObject o, JsonDeserializationContext context) {
			return new ItemSpade(getToolMaterial(o.get("name").getAsString()));
		}
	}

	public static class ItemSwordFactory implements Factory<ItemSword> {

		@Override
		public ItemSword newInstance(JsonObject o, JsonDeserializationContext context) {
			return new ItemSword(getToolMaterial(o.get("name").getAsString()));
		}
	}

	public static class ItemAxeFactory implements Factory<ItemAxe> {

		@Override
		public ItemAxe newInstance(JsonObject o, JsonDeserializationContext context) {
			return new ItemAxe(getToolMaterial(o.get("name").getAsString())) {};
		}
	}

	private static ToolMaterial getToolMaterial(String name) {
		for (ToolMaterial material: ToolMaterial.values()) {
			if (material.toString().equals(name)) {
				return material;
			}
		}
		return null;
	}
}