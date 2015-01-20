package com.github.coolsquid.squidutils.util;

import java.util.Random;

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
	
	private static Random r = new Random();
	
	/**
	 * Has a d/k chance to return true.
	 * @param d
	 * @param k
	 * @return boolean
	 */
	
	public static boolean getChance(int d, int k) {
		int a = r.nextInt(k) + 1;
		return a <= d;
	}
}