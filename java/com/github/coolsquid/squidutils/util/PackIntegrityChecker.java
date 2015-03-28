/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.Loader;

public class PackIntegrityChecker implements UncaughtExceptionHandler {

	public static final PackIntegrityChecker INSTANCE = new PackIntegrityChecker();

	private PackIntegrityChecker() {
		
	}

	/** List of original pack mods. */
	private final List<String> allModsRequired = new ArrayList<String>();

	/** Optional mods. */
	private final List<String> optionalMods = new ArrayList<String>();

	/** List of removed mods. */
	private final List<String> missingMods = new ArrayList<String>();

	/** List of added mods. */
	private final List<String> addedMods = new ArrayList<String>();

	/** Checks if mods are removed/added. */
	public void check() {
		for (int a = 0; a < ConfigHandler.INSTANCE.modList.length; a++) {
			this.allModsRequired.add(ConfigHandler.INSTANCE.modList[a]);
		}
		if (!this.allModsRequired.contains("mcp")) {this.allModsRequired.add("mcp");}
		if (!this.allModsRequired.contains("Forge")) {this.allModsRequired.add("Forge");}
		if (!this.allModsRequired.contains("fml")) {this.allModsRequired.add("FML");}
		if (!this.allModsRequired.contains("SquidAPI")) {this.allModsRequired.add("SquidAPI");}
		if (!this.allModsRequired.contains(ModInfo.modid)) {this.allModsRequired.add(ModInfo.modid);}
		for (int a = 0; a < ConfigHandler.INSTANCE.optionalMods.length; a++) {
			this.optionalMods.add(ConfigHandler.INSTANCE.optionalMods[a]);
		}
		for (int a = 0; a < ConfigHandler.INSTANCE.modList.length; a++) {
			if (!Loader.isModLoaded(this.allModsRequired.get(a))) {
				this.missingMods.add(ConfigHandler.INSTANCE.modList[a]);
			}
		}
		for (int a = 0; a < Loader.instance().getModList().size(); a++) {
			if (!this.allModsRequired.contains(Loader.instance().getModList().get(a).getModId())) {
				if (!this.optionalMods.contains(Loader.instance().getModList().get(a).getModId())) {
					this.addedMods.add(Loader.instance().getModList().get(a).getModId());
				}
			}
		}
		this.warn();
	}
	
	public void warn() {
		if (!this.missingMods.isEmpty()) {
			LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!! Missing mods:");
			if (!(this.missingMods.isEmpty() || this.addedMods.isEmpty())) {
				LogHelper.bigWarning(Level.WARN, "The modpack has been modified. DO NOT REPORT ANY BUGS!!!");
				if (!this.missingMods.isEmpty()) {
					LogHelper.warn("Missing mods:");
					for (int a = 0; a < this.missingMods.size(); a++) {
						LogHelper.warn(this.missingMods.get(a));
					}
				}
				if (!this.addedMods.isEmpty()) {
					LogHelper.warn("Added mods:");
					for (int a = 0; a < this.addedMods.size(); a++) {
						LogHelper.warn(this.addedMods.get(a));
					}
				}
			}
		}
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		this.warn();
	}
	
	public boolean haveModsBeenRemoved() {
		return !this.missingMods.isEmpty();
	}
	
	public boolean haveModsBeenAdded() {
		return !this.addedMods.isEmpty();
	}
}