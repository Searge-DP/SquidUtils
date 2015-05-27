/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public interface Factory<T> {
	public T newInstance(JsonObject o, JsonDeserializationContext context);
}