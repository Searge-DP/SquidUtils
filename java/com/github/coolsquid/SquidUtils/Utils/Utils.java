package com.github.coolsquid.SquidUtils.Utils;

import java.util.Random;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class Utils {
	
	private static Random rand = new Random();
		
	public static boolean getChance(int d, int k) {
		int a = rand.nextInt(k) + 1;
		return a <= d;
	}
}