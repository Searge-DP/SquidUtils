package com.github.coolsquid.SquidUtils.Utils.Exception;

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