package com.github.coolsquid.SquidUtils.Utils.Exception;


public class SquidUtilsException extends RuntimeException {
	
	private static final long serialVersionUID = -6117434239809129613L;
	
	private static String s;
	public SquidUtilsException(String comment) {
		crash(comment);
	}
	
	public SquidUtilsException() {
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