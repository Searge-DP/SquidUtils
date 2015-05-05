/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import cpw.mods.fml.common.eventhandler.EventBus;

public interface IEventHandlerRegistrationManager {
	public abstract void register(Object handler, EventBus bus);
}