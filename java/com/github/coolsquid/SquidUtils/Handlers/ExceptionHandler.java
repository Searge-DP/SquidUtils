package com.github.coolsquid.SquidUtils.Handlers;

import java.util.ArrayList;

public class ExceptionHandler {

	private static final ArrayList<String> LIST = new ArrayList<String>();
	
	public static void arrayInit() {
		LIST.add(".technic");
		LIST.add(".Technic");
		LIST.add("Technic");
		LIST.add("technic");
		init();
	}

	public static void init() {
		
		String dir = System.getProperty("user.dir");
		
		int A = 0;
		while (A < LIST.size()) {
			if (dir.contains(LIST.get(A))) {
				PermissionException();
			}
			A++;
		}
	}
	
	public static void PermissionException() {
		throw new NullPointerException("no_permission_exception");
	}
}