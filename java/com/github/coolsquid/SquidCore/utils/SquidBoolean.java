package com.github.coolsquid.SquidCore.utils;

import java.util.Random;

/**
* 
* @author CoolSquid
* All rights reserved.
*
*/

public class SquidBoolean {
	
	static Random rand = new Random();
	
	public static boolean half() {

		int a = rand.nextInt(2) + 1;
		if (a == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean third() {

		int a = rand.nextInt(3) + 1;
		if (a == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean fourth() {

		int a = rand.nextInt(4) + 1;
		if (a == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean fifth() {

		int a = rand.nextInt(5) + 1;
		if (a == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
