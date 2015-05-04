/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import cpw.mods.fml.common.eventhandler.EventBus;

public class EventHandler {

	private final EventBus bus;
	private final Object handler;

	public EventHandler(EventBus bus, Object handler) {
		this.bus = bus;
		this.handler = handler;
	}

	public EventBus getBus() {
		return this.bus;
	}

	public Object getHandler() {
		return this.handler;
	}
}