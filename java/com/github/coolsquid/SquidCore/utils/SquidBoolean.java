package com.github.coolsquid.SquidCore.utils;

import java.util.Random;

public class SquidBoolean {
	
	/**
	 * 
	 * @author CoolSquid
	 * All rights reserved.
	 *
	 */
	
	static Random rand = new Random();
	
	public static boolean half() {

		int a = rand.nextInt(2) + 1;
		if (a == 2) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static boolean third() {

		int a = rand.nextInt(3) + 1;
		if (a >= 2) {
			return false;
		}
		else {
			return true;
		}
	}

	public static boolean fourth() {

		int a = rand.nextInt(4) + 1;
		if (a >= 2) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static boolean fifth() {

		int a = rand.nextInt(5) + 1;
		if (a >= 2) {
			return false;
		}
		else {
			return true;
		}
	}
}
