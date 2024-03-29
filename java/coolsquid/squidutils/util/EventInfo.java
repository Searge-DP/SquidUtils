/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.util.HashMap;
import java.util.Map;

public class EventInfo {

	public Map<String, Object> values = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public <E> Map<String, E> getValues() {
		return (Map<String, E>) this.values;
	}

	public void addValue(String key, String value) {
		this.values.put(key, value);
	}
}