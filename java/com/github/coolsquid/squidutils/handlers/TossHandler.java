/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraftforge.event.entity.item.ItemTossEvent;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TossHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onToss(ItemTossEvent event) {
		for (EventInfo a: info) {
			if (a.getItem() == event.entityItem.getEntityItem().getItem()) {
				EventEffectHelper.performEffects(a, event.player);
			}
		}
	}
}