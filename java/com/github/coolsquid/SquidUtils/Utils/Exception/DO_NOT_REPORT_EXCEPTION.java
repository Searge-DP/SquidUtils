package com.github.coolsquid.SquidUtils.Utils.Exception;



public class DO_NOT_REPORT_EXCEPTION extends RuntimeException {
	
	private static final long serialVersionUID = 434986525;
	
	String comment;
	
	public DO_NOT_REPORT_EXCEPTION() {
		comment = "This is not SquidUtils fault!";
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return comment;
	}
}