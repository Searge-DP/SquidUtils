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
	 * Optional mods.
	 */
	
	private static final ArrayList<String> optionalMods = new ArrayList<String>();
	
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
		for (int a = 0; a < ConfigHandler.modList.length; a++) {
			allModsRequired.add(ConfigHandler.modList[a]);
		}
		if (!allModsRequired.contains("mcp")) {allModsRequired.add("mcp");}
		if (!allModsRequired.contains("Forge")) {allModsRequired.add("Forge");}
		if (!allModsRequired.contains("fml")) {allModsRequired.add("fml");}
		if (!allModsRequired.contains("SquidAPI")) {allModsRequired.add("SquidAPI");}
		if (!allModsRequired.contains(ModInfo.modid)) {allModsRequired.add(ModInfo.modid);}
		for (int a = 0; a < ConfigHandler.optionalMods.length; a++) {
			optionalMods.add(ConfigHandler.optionalMods[a]);
		}
		for (int a = 0; a < ConfigHandler.modList.length; a++) {
			if (!Loader.isModLoaded(allModsRequired.get(a))) {
				missingMods.add(ConfigHandler.modList[a]);
			}
		}
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			if (!allModsRequired.contains(Loader.instance().getModList().get(a).getModId())) {
				if (!optionalMods.contains(Loader.instance().getModList().get(a).getModId())) {
					addedMods.add(Loader.instance().getModList().get(a).getModId());
				}
			}
		}
		warn();
	}
	
	public static final void warn() {
		if (!missingMods.isEmpty()) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!! Missing mods:");
			if (!(missingMods.isEmpty() || addedMods.isEmpty())) {
				LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
				if (!missingMods.isEmpty()) {
					LogHelper.warn("Missing mods:");
					for (int a = 0; a < missingMods.size(); a++) {
						LogHelper.warn(missingMods.get(a));
					}
				}
				if (!addedMods.isEmpty()) {
					LogHelper.warn("Added mods:");
					for (int a = 0; a < addedMods.size(); a++) {
						LogHelper.warn(addedMods.get(a));
					}
				}
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		warn();
	}
	
	public static final boolean areModsMissing() {
		return !missingMods.isEmpty();
	}
	
	public static final boolean areModsAdded() {
		return !addedMods.isEmpty();
	}
}