package com.github.coolsquid.Testy.Utils.Reflection;

import com.github.coolsquid.Testy.Utils.Exception.TestyRuntimeException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ReflectionException extends TestyRuntimeException {
	private static final long serialVersionUID = 8573921006074521771L;
	public ReflectionException(String comment) throws Exception {
		super(comment);
	}
	
	public ReflectionException() {
		super();
	}
}