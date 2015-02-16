/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.item.ItemTossEvent;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TossHandler {
	
	public static final EventInfo info = new EventInfo();
	
	@SubscribeEvent
	public void onToss(ItemTossEvent event) {
		EventEffectHelper.run(info.getKeytoinfo(), event.entityItem.getEntityItem().getItem(), event.player);
	}
}