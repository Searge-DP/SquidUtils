package com.github.coolsquid.SquidUtils.Handlers.ArrayHandlers;

import java.util.ArrayList;

public class BooleanHandler {
	
	private static final ArrayList<Boolean> BooleanList = new ArrayList<Boolean>();

	public static boolean doesListEntryExist(String string) {
		return BooleanList.contains(string);
	}
	
	public static void addToList(Boolean value) {
		BooleanList.add(value);
	}
}