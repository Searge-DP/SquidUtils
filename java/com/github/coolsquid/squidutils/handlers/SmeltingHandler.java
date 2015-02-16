/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class SmeltingHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();

	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event) {
		for (EventInfo a: info) {
			if (a.getItem() == event.smelting.getItem()) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}