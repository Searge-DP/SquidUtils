package com.github.coolsquid.SquidUtils.Utils;

import net.minecraft.client.entity.EntityClientPlayerMP;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Utils {
	
	public static final void sendMsg(EntityClientPlayerMP player, String msg) {
		player.sendChatMessage(msg);
	}
}