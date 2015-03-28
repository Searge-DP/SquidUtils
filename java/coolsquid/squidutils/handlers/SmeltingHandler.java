/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class SmeltingHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("item") || a.values.get("item") == event.smelting.getItem()) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}