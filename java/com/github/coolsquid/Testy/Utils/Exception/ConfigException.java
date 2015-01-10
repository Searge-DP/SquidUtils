package com.github.coolsquid.Testy.Utils.Exception;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ConfigException extends RuntimeException {
	
	private static final long serialVersionUID = 2391612540827275495L;
	
	public ConfigException(String comment) {
		throw this;
	}
}