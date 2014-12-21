package com.github.coolsquid.SquidUtils.Utils.Exception;

public class SomethingWentWrongException extends RuntimeException {
	
	private static final long serialVersionUID = 434986524;
	
	String comment = "Something went wrong.";
	
	public SomethingWentWrongException() {
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return comment;
	}
}