/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import coolsquid.squidutils.SquidUtils;

public class CustomContentManager {

	public static final CustomContentManager INSTANCE = new CustomContentManager();

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final List<CreationHandler<?>> handlers = Lists.newArrayList();

	public CustomContentManager() {
		this.registerDeserializer(new JsonDeserializer<Item>() {
			@Override
			public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return (Item) Item.itemRegistry.getObject(json.getAsString());
			}
		}, Item.class);
		this.registerDeserializer(new JsonDeserializer<Block>() {
			@Override
			public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				return (Block) Block.blockRegistry.getObject(json.getAsString());
			}
		}, Item.class);
	}

	public void registerHandlers(CreationHandler<?>... handlers) {
		for (CreationHandler<?> handler: handlers) {
			this.handlers.add(handler);
		}
	}

	public void loadAll() {
		Gson gson = this.gsonBuilder.create();
		for (CreationHandler<?> handler: this.handlers) {
			try {
				handler.load(gson);
			} catch (Throwable t) {
				SquidUtils.INSTANCE.catching(t);
			}
		}
	}

	public void registerDeserializer(JsonDeserializer<?> deserializer, Type type) {
		this.gsonBuilder.registerTypeAdapter(type, deserializer);
	}

	public void registerInstanceCreator(InstanceCreator<?> instanceCreator, Type type) {
		this.gsonBuilder.registerTypeAdapter(type, instanceCreator);
	}

	public GsonBuilder getGsonBuilder() {
		return this.gsonBuilder;
	}
}