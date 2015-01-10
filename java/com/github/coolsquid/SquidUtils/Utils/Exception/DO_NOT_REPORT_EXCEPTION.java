package com.github.coolsquid.SquidUtils.Utils.Exception;

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