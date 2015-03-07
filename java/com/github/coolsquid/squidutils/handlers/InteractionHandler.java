/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.github.coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InteractionHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
			if (event.entityPlayer.getHeldItem() != null) {
				Item item = event.entityPlayer.getHeldItem().getItem();
				for (EventInfo a: info) {
					if (!a.values.containsKey("item") || a.values.get("item") == item) {
						EventEffectHelper.performEffects(a, event.entityLiving);
					}
				}
			}
		}
	}
}