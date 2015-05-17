/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("item") || a.values.get("item") == event.crafting.getItem()) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}