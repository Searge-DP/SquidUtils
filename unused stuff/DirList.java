package com.github.coolsquid.squidutils.util;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.Testy.Registry.SimpleRegistry;
import com.github.coolsquid.squidutils.helpers.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * Will be used for modpacks stealing SquidUtils
 * 
 */

public class DirList {
	
	/**
	 * Registry of folder names for illegal packs.
	 */
	
	public static final SimpleRegistry DIR_LIST = new SimpleRegistry();
	
	public static final void warn() {
		LogHelper.bigWarning(Level.WARN, "You are running an illegal modpack! Please report this at: " + Data.forum);
	}
	
	public static final void add(String dir) {
		DIR_LIST.register(dir);
	}
}