/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import squeek.applecore.api.hunger.StarvationEvent;
import coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void event(StarvationEvent.Starve event) {
		event.starveDamage = ModConfigHandler.INSTANCE.starvationDamage;
		for (EventInfo a: info) EventEffectHelper.performEffects(a, event.player);
	}
}