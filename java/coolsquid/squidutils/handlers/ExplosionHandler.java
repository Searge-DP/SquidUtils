/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.world.ExplosionEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExplosionHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void onExplosionStart(ExplosionEvent.Start event) {
		event.explosion.explosionSize = event.explosion.explosionSize * ModConfigHandler.INSTANCE.explosionSizeMultiplier;
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a);
		}
	}
}