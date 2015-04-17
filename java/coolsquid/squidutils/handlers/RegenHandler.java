/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import squeek.applecore.api.hunger.HealthRegenEvent;
import coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import coolsquid.squidutils.config.GeneralConfigHandler;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegenHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void event(HealthRegenEvent.AllowRegen event) {
		if (GeneralConfigHandler.INSTANCE.noHungerRegen) {
			event.setResult(Result.DENY);
			return;
		}
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.player);
			if (a.values.get("cancel").equals("true")) event.setCanceled(true);
		}
	}
}