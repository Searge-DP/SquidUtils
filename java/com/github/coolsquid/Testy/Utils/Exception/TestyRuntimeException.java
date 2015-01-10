package com.github.coolsquid.Testy.Utils.Exception;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class TestyRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = -6117434239809129613L;
	
	private static String s;
	public TestyRuntimeException(String comment) {
		crash(comment);
	}
	
	public TestyRuntimeException() {
		crash();
	}
	
	public void crash(String comment) {
		s = comment;
		throw this;
	}
	
	public void crash() {
		s = "";
		throw this;
	}
	
	@Override
	public String getMessage() {
		return s;
	}
}