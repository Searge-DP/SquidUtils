package com.github.coolsquid.SquidUtils.Handlers;

import java.util.ArrayList;

public class ExceptionHandler {

	private static final ArrayList<String> DIR_LIST = new ArrayList<String>();
	
	public static void arrayInit() {
		DIR_LIST.add(".technic");
		DIR_LIST.add(".Technic");
		DIR_LIST.add("Technic");
		DIR_LIST.add("technic");
		init();
	}

	public static void init() {
		
		String dir = System.getProperty("user.dir");
		
		int A = 0;
		while (A < DIR_LIST.size()) {
			if (dir.contains(DIR_LIST.get(A))) {
				PermissionException();
			}
			A++;
		}
	}
	
	public static void PermissionException() {
		throw new NullPointerException("no_permission_exception");
	}
}
