package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InteractionHandler {
	
	public static final ArrayList<EventInfo> info = new ArrayList<EventInfo>();
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
			if (event.entityPlayer.getHeldItem() != null) {
				Item item = event.entityPlayer.getHeldItem().getItem();
				for (EventInfo a: info) {
					if (a.getItem() == item) {
						EventEffectHelper.performEffects(a, event.entityLiving);
					}
				}
			}
		}
	}
}