/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.HashMap;

import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BreakSpeedHandler {
	
	public static final HashMap<Integer, Float> layers = Maps.newHashMap();
	
	@SubscribeEvent
	public void breakSpeed(BreakSpeed event) {
		for (int a = event.y;; a++) {
			Float b = layers.get(a);
			if (b != null && b != 1F) {
				event.newSpeed = event.originalSpeed / layers.get(a);
				return;
			}
		}
	}
}