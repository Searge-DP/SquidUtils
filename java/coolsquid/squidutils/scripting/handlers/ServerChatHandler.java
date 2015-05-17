/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.ServerChatEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerChatHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("chattrigger") || event.message.contains((String) a.values.get("chattrigger"))) {
				EventEffectHelper.performEffects(a, event.player);
				if (a.values.get("cancel") != null) {
					event.setCanceled(true);
				}
			}
		}
	}
}