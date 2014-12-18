package com.github.coolsquid.SquidUtils.Exception;

public class InvalidConfigValueException extends RuntimeException {
	
	private static final long serialVersionUID = 434986523;
	
	String comment;
	
	public InvalidConfigValueException(String string) {
		comment = "The config value " + "\"" + string + "\" has a bad value";
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return comment;
	}
}