/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;

import coolsquid.squidutils.SquidUtils;

public class CustomContentManager {

	public static final CustomContentManager INSTANCE = new CustomContentManager();

	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final List<CustomContentHandler<?>> handlers = Lists.newArrayList();

	public void registerHandlers(CustomContentHandler<?>... handlers) {
		for (CustomContentHandler<?> handler: handlers) {
			this.handlers.add(handler);
		}
	}

	public void loadAll() {
		Gson gson = this.gsonBuilder.create();
		for (CustomContentHandler<?> handler: this.handlers) {
			try {
				handler.load(gson);
			} catch (Throwable t) {
				SquidUtils.instance().catching(t);
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