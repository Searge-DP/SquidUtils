/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import squeek.applecore.api.hunger.HealthRegenEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void event(HealthRegenEvent.AllowRegen event) {
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.player);
			if (a.values.get("cancel").equals("true")) {
				event.setCanceled(true);
			}
		}
	}
}