/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.github.coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ConfigHandler.disableTeleportation) event.setCanceled(true);
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.entityLiving);
		}
	}
}