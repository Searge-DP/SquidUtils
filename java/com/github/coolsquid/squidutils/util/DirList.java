package com.github.coolsquid.squidutils.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.squidutils.util.logging.LogHelper;

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
	
	private static List<String> DIR_LIST = new ArrayList<String>();
	
	public static final void warn() {
		LogHelper.bigWarning(Level.WARN, "You are running an illegal modpack! Please report this at: " + Data.forum);
	}
	
	public static final void add(String dir) {
		DIR_LIST.add(dir);
	}
}