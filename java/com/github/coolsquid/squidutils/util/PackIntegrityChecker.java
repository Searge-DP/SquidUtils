package com.github.coolsquid.squidutils.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.util.logging.LogHelper;

import cpw.mods.fml.common.Loader;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * Checks if any mods have been added/removed from the pack.
 * @since 1.1.4
 * 
 */

public class PackIntegrityChecker implements UncaughtExceptionHandler {
	
	/**
	 * List of original pack mods.
	 */
	
	private static final ArrayList<String> allModsRequired = new ArrayList<String>();
	
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
	
	public static final void check() {
		if (Loader.instance().getModList().toArray() != ConfigHandler.modList)
			check2();
	}
	
	public static final void check2() {
		int a4 = 0;
		while (a4 < ConfigHandler.modList.length) {
			allModsRequired.add(ConfigHandler.modList[a4]);
			a4++;
		}
		int a = 0;
		while (a < ConfigHandler.modList.length) {
			if (!Loader.isModLoaded(ConfigHandler.modList[a])) {
				missingMods.add(ConfigHandler.modList[a]);
			}
			a++;
		}
		int b = 0;
		while (b < allModsRequired.size()) {
			if (!allModsRequired.contains(Loader.instance().getModList().get(b).getModId())) {
				addedMods.add(Loader.instance().getModList().get(b).getModId());
			}
			b++;
		}
		if (!(missingMods.isEmpty() || addedMods.isEmpty())) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
			if (!missingMods.isEmpty()) {
				LogHelper.warn("Missing mods:");
				int a2 = 0;
				while (a2 < missingMods.size()) {
					LogHelper.warn(missingMods.get(a2));
					a2++;
				}
			}
			if (!addedMods.isEmpty()) {
				LogHelper.warn("Added mods:");
				int a3 = 0;
				while (a3 < addedMods.size()) {
					LogHelper.warn(addedMods.get(a3));
					a3++;
				}
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (!missingMods.isEmpty()) {
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