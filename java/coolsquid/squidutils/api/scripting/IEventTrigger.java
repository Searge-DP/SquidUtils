/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.scripting;

import java.util.List;

import coolsquid.squidutils.util.script.EventInfo;

public interface IEventTrigger {
	public abstract List<EventInfo> info();
}