package com.github.coolsquid.SquidUtils.Exception;

public class DO_NOT_REPORT_EXCEPTION extends RuntimeException {
	
	private static final long serialVersionUID = 434986525;
	
	String comment = "This is not SquidUtils fault!";
	
	public DO_NOT_REPORT_EXCEPTION() {
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return comment;
	}
}