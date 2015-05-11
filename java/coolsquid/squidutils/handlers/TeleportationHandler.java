/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TeleportationHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void event(EnderTeleportEvent event) {
		if (ModConfigHandler.INSTANCE.disableTeleportation) event.setCanceled(true);
		for (EventInfo a: info) {
			EventEffectHelper.performEffects(a, event.entityLiving);
		}
	}
}