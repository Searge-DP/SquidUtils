package com.github.coolsquid.SquidUtils.Utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

import cpw.mods.fml.common.Loader;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * Checks if any mods have been added/removed from the pack.
 *
 */

public class PackIntegrityChecker implements UncaughtExceptionHandler {
	
	private static final String[] modList = ConfigHandler.modList;
	
	/**
	 * List of removed mods.
	 */
	
	private static final ArrayList<String> missingMods = new ArrayList<String>();
	
	/**
	 * List of added mods.
	 */
	
	private static final ArrayList<String> addedMods = new ArrayList<String>();
	
	/**
	 * Checks if mods are removed/added.
	 */
	
	public static void check() {
		int a = 0;
		while (a < modList.length) {
			if (!Loader.isModLoaded(modList[a])) {
				missingMods.add(modList[a]);
			}
			a++;
		}
		if (modList.length != 0) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
			LogHelper.warn("Missing mods:");
			int a2 = 0;
			while (a2 < missingMods.size()) {
				LogHelper.warn(missingMods.get(a2));
				a2++;
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (modList.length != 0) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!! Missing mods:");
			int a = 0;
			while (a < missingMods.size()) {
				LogHelper.warn(missingMods.get(a));
			}
		}
	}
	
	public static final boolean areModsMissing() {
		return !missingMods.isEmpty();
	}
	
	public static final boolean areModsAdded() {
		return !addedMods.isEmpty();
	}
}