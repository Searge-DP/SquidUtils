/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import coolsquid.squidapi.util.version.UpdateChecker;
import coolsquid.squidapi.util.version.UpdateManager;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

public class UpdateCheckerCreationHandler extends CustomContentHandler<UpdateChecker> {

	public static final UpdateCheckerCreationHandler INSTANCE = new UpdateCheckerCreationHandler();

	public UpdateCheckerCreationHandler() {
		super("updatecheckers", UpdateChecker.class);
	}

	@Override
	protected void handle(UpdateChecker fromJson) {
		UpdateManager.INSTANCE.registerUpdateChecker(fromJson);
	}

	static {
		CustomContentManager.INSTANCE.registerDeserializer(new UpdateCheckerDeserializer(), UpdateChecker.class);
	}

	private static class UpdateCheckerDeserializer implements JsonDeserializer<UpdateChecker> {

		@Override
		public UpdateChecker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject o = json.getAsJsonObject();
			ModMetadata meta = new ModMetadata();
			String name = o.get("name").getAsString();
			meta.modId = name;
			meta.name = name;
			meta.version = o.get("version").getAsString();
			meta.url = o.get("friendlyUrl").getAsString();
			return new UpdateChecker(new DummyModContainer(meta), o.get("url").getAsString());
		}
	}
}