package com.github.coolsquid.SquidUtils.Utils.Exception;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class InvalidConfigValueException extends SquidUtilsException {
	private static final long serialVersionUID = 434986523;
		
	public InvalidConfigValueException(String comment) {
		super(comment);
	}
}