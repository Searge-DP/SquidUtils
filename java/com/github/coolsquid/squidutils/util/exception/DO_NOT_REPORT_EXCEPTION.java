package com.github.coolsquid.squidutils.util.exception;

import com.github.coolsquid.Testy.Utils.Exception.TestyRuntimeException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DO_NOT_REPORT_EXCEPTION extends TestyRuntimeException {
	private static final long serialVersionUID = 6583935765453828886L;
	
	public DO_NOT_REPORT_EXCEPTION(String comment) {
		super(comment);
	}
}