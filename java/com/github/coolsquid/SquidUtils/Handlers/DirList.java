package com.github.coolsquid.SquidUtils.Handlers;

import java.util.ArrayList;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * List off pack directories
 *
 */

public class DirList {
	
	private static final ArrayList<String> DIR_LIST = new ArrayList<String>();
	
	public static void arrayInit() {
		add("Technic");
		add("technic");
		add("tekkit");
		add("Tekkit");
	}
	
	public static String get(int i) {
		return DIR_LIST.get(i);
	}
	
	public static int size() {
		return DIR_LIST.size();
	}
	
	static void add(String string) {
		DIR_LIST.add(string);
	}
}