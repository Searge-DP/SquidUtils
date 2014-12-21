package com.github.coolsquid.SquidUtils.Utils;

public class InvalidConfigValueException extends RuntimeException {
	
	private static final long serialVersionUID = 434986523;
	
	String comment;
	
	public InvalidConfigValueException(String string) {
		comment = "The config value " + "\"" + string + "\" has an invalid value\n	THIS IS NOT A MOD ERROR. DO NOT REPORT THIS!";
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return comment;
	}
}