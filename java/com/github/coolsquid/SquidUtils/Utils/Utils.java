package com.github.coolsquid.SquidUtils.utils;

import java.util.Random;

import cpw.mods.fml.common.Loader;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class Utils {
	
	static Random rand = new Random();
	
	public static boolean chance(int D, int K) {

		int A = rand.nextInt(K) + 1;
		if (A <= D) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isModNotLoaded(String mod) {
		if (Loader.isModLoaded(mod)) {
			return false;
		}
		else {
			return true;
		}
	}
}
