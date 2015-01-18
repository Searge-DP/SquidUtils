package com.github.coolsquid.squidutils.util.exception;

import com.github.coolsquid.Testy.Utils.Exception.TestyRuntimeException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class InvalidConfigValueException extends TestyRuntimeException {
	private static final long serialVersionUID = 434986523;
		
	public InvalidConfigValueException(String comment) {
		super(comment);
	}
}