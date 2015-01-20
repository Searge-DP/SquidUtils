package com.github.coolsquid.squidutils.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;

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
		int a = 0;
		while (a < ConfigHandler.modList.length) {
			allModsRequired.add(ConfigHandler.modList[a]);
			a++;
		}
		allModsRequired.add("mcp");
		allModsRequired.add("Forge");
		allModsRequired.add("FML");
		allModsRequired.add(Data.modid);
		int b = 0;
		while (b < ConfigHandler.modList.length) {
			if (!Loader.isModLoaded(allModsRequired.get(b))) {
				missingMods.add(ConfigHandler.modList[b]);
			}
			b++;
		}
		int c = 0;
		while (c < Loader.instance().getModList().size()) {
			if (!allModsRequired.contains(Loader.instance().getModList().get(c).getModId())) {
				addedMods.add(Loader.instance().getModList().get(c).getModId());
			}
			c++;
		}
		warn();
	}
	
	public static final void warn() {
		if (!(missingMods.isEmpty() || addedMods.isEmpty())) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
			if (!missingMods.isEmpty()) {
				LogHelper.warn("Missing mods:");
				int d = 0;
				while (d < missingMods.size()) {
					LogHelper.warn(missingMods.get(d));
					d++;
				}
			}
			if (!addedMods.isEmpty()) {
				LogHelper.warn("Added mods:");
				int e = 0;
				while (e < addedMods.size()) {
					LogHelper.warn(addedMods.get(e));
					e++;
				}
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (!missingMods.isEmpty()) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!! Missing mods:");
			if (!(missingMods.isEmpty() || addedMods.isEmpty())) {
				LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
				if (!missingMods.isEmpty()) {
					LogHelper.warn("Missing mods:");
					int a = 0;
					while (a < missingMods.size()) {
						LogHelper.warn(missingMods.get(a));
						a++;
					}
				}
				if (!addedMods.isEmpty()) {
					LogHelper.warn("Added mods:");
					int b = 0;
					while (b < addedMods.size()) {
						LogHelper.warn(addedMods.get(b));
						b++;
					}
				}
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