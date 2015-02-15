/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class SmeltingHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event) {
		EventEffectHelper.run(info.getKeytoinfo(), event.smelting.getItem(), event.player);
	}
}