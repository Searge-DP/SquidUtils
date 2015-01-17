package com.github.coolsquid.Testy.Utils.Logging;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * All available levels for the Testy logging library.
 */

public enum Level {
	
	/**
	 * Indicates that the message is printed for information purposes.
	 */
	
	INFO,
	
	/**
	 * Indicates that something might fail.
	 */
	
	WARN,
	
	/**
	 * Indicates that an error has occured.
	 */
	
	ERROR,
	
	/**
	 * Indicates that a fatal error has occured, and that the program will exit.
	 */
	
	FATAL;
}