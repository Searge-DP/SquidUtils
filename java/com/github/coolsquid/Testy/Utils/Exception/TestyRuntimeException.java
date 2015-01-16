package com.github.coolsquid.Testy.Utils.Exception;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * My RuntimeException superclass.
 *
 */

public class TestyRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = -6117434239809129613L;
	private static Throwable t;
	private static String s;
	public TestyRuntimeException(String comment) {
		crash(comment);
	}
	
	public TestyRuntimeException() {
		crash();
	}
	
	public TestyRuntimeException(Throwable cause) {
		t = cause;
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
	
	@Override
	public Throwable getCause() {
		return t;
	}
}