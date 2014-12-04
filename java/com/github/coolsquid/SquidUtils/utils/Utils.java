package com.github.coolsquid.SquidUtils.utils;

import java.util.Random;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class Utils {
	
	static Random rand = new Random();
	
	public static boolean chance(int D, int K) {

		int A = rand.nextInt(D) + 1;
		if (A <= K) {
			return true;
		}
		else {
			return false;
		}
	}
}
