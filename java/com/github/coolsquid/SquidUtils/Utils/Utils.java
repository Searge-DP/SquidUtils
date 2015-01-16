package com.github.coolsquid.SquidUtils.Utils;

import net.minecraft.client.entity.EntityClientPlayerMP;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Utils {
	
	/**
	 * Sends a chat message to a player.
	 * @param player
	 * @param msg
	 */
	
	public static final void sendMsg(EntityClientPlayerMP player, String msg) {
		player.sendChatMessage(msg);
	}
}