package com.github.coolsquid.SquidUtils.Utils;

import net.minecraft.client.entity.EntityClientPlayerMP;

public class Utils {
	
	public static final void sendMsg(EntityClientPlayerMP player, String msg) {
		player.sendChatMessage(msg);
	}
}