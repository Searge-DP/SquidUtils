/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidapi.util.StringParser;

public class AchievementCreationHandler extends CreationHandler<Achievement> {

	public static final AchievementCreationHandler INSTANCE = new AchievementCreationHandler();

	public AchievementCreationHandler() {
		super("achievements", Achievement[].class);
	}

	@Override
	protected void handle(Achievement fromJson) {
		fromJson.registerStat();
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new AchievementDeserializer(), Achievement.class);
	}

	public static class AchievementDeserializer implements JsonDeserializer<Achievement> {

		@Override
		public Achievement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			String name = o.get("name").getAsString();
			JsonElement parent = o.get("parent");
			return new Achievement(name, name, o.get("column").getAsInt(), o.get("row").getAsInt(), (Item) Item.itemRegistry.getObject(o.get("item").getAsString()), parent != null ? StringParser.parseAchievement(parent.getAsString()) : null);
		}
	}
}