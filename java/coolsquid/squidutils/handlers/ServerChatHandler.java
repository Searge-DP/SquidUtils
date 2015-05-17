/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.ServerChatEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerChatHandler {

	@SubscribeEvent
	public void onChat(ServerChatEvent event) {
		if (event.message.length() < ModConfigHandler.INSTANCE.minMessageLength) {
			event.setCanceled(true);
			return;
		}
	}
}