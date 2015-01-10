package com.github.coolsquid.Testy.Utils.Exception;


/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class BadInputException extends RuntimeException {
	
	private static final long serialVersionUID = -7984016235481466214L;
	public static final int EID = 764;
	
	public BadInputException() {
		throw this;
	}
}