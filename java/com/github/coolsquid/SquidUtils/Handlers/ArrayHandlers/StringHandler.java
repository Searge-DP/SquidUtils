package com.github.coolsquid.SquidUtils.Handlers.ArrayHandlers;

import java.util.ArrayList;

public class StringHandler {
	
	private static final ArrayList<Item> LIST = new ArrayList<Item>();

	public static boolean doesListEntryExist(Item item) {
		return LIST.contains(item);
	}
	
	public static void addToList(Item item) {
		LIST.add(item);
	}
}
