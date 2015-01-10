package com.github.coolsquid.Testy.Utils.Exception;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class TestyException extends Exception {
	
	private static final long serialVersionUID = -6117434239809129613L;
	
	private static String s;
	public TestyException(String comment) throws TestyException {
		crash(comment);
	}
	
	public TestyException() throws TestyException {
		crash();
	}
	
	public void crash(String comment) throws TestyException {
		s = comment;
		throw this;
	}
	
	public void crash() throws TestyException {
		s = "";
		throw this;
	}
	
	@Override
	public String getMessage() {
		return s;
	}
}