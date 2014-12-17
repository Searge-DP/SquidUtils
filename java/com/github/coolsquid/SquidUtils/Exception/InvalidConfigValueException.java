package com.github.coolsquid.SquidUtils.Exception;

public class InvalidConfigValueException extends RuntimeException {
	private static final long serialVersionUID = 434986523;
	private static final StringBuilder reason = new StringBuilder();
	public InvalidConfigValueException(String string) {
		reason.append("The config value " + "\"" + string + "\" has a bad value");
		this.stop();
	}
	
	final void stop() {
		throw this;
	}
	
	@Override
	public final String getMessage() {
		return reason.toString();
	}
}