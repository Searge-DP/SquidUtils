/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BreakSpeedHandler {
	
	@SubscribeEvent
	public void breakSpeed(BreakSpeed event) {
		if (event.y < 10) {
			event.newSpeed = event.originalSpeed / 8;
		}
		else if (event.y < 30) {
			event.newSpeed = event.originalSpeed / 6;
		}
		else if (event.y < 50) {
			event.newSpeed = event.originalSpeed / 4;
		}
		else if (event.y < 63) {
			event.newSpeed = event.originalSpeed / 2;
		}
	}
}