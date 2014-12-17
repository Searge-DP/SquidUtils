package com.github.coolsquid.SquidUtils.Utils;

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
	
	public static final void startInit() {
		int A = 0;
		if (A == 0) {
			arrayInit();
			Init();
			A++;
		}
	}
	
	private static void arrayInit() {
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
	
	private static void add(String string) {
		DIR_LIST.add(string);
	}
	
	private static void Init() {
		String dir = System.getProperty("user.dir");
		int A = 0;
		int B = 0;
		while (A < size() && B == 0) {
			if (dir.contains(get(A))) {
				LogHelper.bigWarning("This modpack might be illegal. Please ask for permission at: " + Reference.forum);
				B++;
				}
			A++;
		}
	}
}