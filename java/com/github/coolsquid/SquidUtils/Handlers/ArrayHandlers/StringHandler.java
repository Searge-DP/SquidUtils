package com.github.coolsquid.SquidUtils.Handlers.ArrayHandlers;

import java.util.ArrayList;

public class StringHandler {
	
	private static final ArrayList<String> LIST = new ArrayList<String>();

	public static boolean doesListEntryExist(String string) {
		return LIST.contains(string);
	}
	
	public static void addToList(String string) {
		LIST.add(string);
	}
}