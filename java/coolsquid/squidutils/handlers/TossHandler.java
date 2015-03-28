/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.item.ItemTossEvent;
import coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TossHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void onToss(ItemTossEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("item") || a.values.get("item") == event.entityItem.getEntityItem().getItem()) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}