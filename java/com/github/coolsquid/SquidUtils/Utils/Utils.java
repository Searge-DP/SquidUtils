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
		
	public static boolean chance(int D, int K) {
		int A = rand.nextInt(K) + 1;
		if (A <= D) {
			return true;
		}
		else {
			return false;
		}
	}
}