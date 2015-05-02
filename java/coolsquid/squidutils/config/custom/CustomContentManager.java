/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

public class CustomContentManager {

	public static final CustomContentManager INSTANCE = new CustomContentManager();

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Set<CustomContentHandler<?>> handlers = Sets.newHashSet();

	public GsonBuilder getGsonBuilder() {
		return this.gsonBuilder;
	}

	public void registerHandlers(CustomContentHandler<?>... handlers) {
		for (CustomContentHandler<?> handler: handlers) {
			this.handlers.add(handler);
		}
	}

	public void loadAll() {
		Gson gson = this.gsonBuilder.create();
		for (CustomContentHandler<?> handler: this.handlers) {
			handler.load(gson);
		}
	}

	public void registerDeserializer(JsonDeserializer<?> deserializer, Type type) {
		this.gsonBuilder.registerTypeAdapter(type, deserializer);
	}
}