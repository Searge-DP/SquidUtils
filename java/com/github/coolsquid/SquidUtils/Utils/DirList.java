package com.github.coolsquid.SquidUtils.Utils;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DirList {
	
	private static final ArrayList<String> DIR_LIST = new ArrayList<String>();
	
	public static final void preInit() {
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
	
	private static final void add(String string) {
		DIR_LIST.add(string);
	}
	
	private static final void Init() {
		String dir = System.getProperty("user.dir");
		int A = 0;
		int B = 0;
		while (A < size() && B == 0) {
			if (dir.contains(get(A))) {
				LogHelper.bigWarning(Level.WARN, "This modpack might be illegal. Please ask for permission at: " + Data.forum);
				B++;
				}
			A++;
		}
	}
}