package com.github.coolsquid.SquidUtils;

import java.util.Random;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class Utils {
	
	static Random rand = new Random();
	
	static int difficultyInt = 1;
	
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