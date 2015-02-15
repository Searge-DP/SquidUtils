/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import com.github.coolsquid.squidutils.helpers.EventEffectHelper;
import com.github.coolsquid.squidutils.util.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler {
	
	public static final EventInfo info = new EventInfo();

	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event) {
		EventEffectHelper.run(info.getKeytoinfo(), event.crafting.getItem(), event.player);
	}
}