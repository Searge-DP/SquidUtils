package com.github.coolsquid.SquidUtils.utils;

import java.util.Random;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class SquidBoolean {
	
	static Random rand = new Random();
	
	public static boolean chance(int D) {

		int a = rand.nextInt(D) + 1;
		if (a == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
