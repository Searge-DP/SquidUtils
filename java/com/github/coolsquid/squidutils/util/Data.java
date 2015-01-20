package com.github.coolsquid.squidutils.util;

import net.minecraft.launchwrapper.Launch;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 * 
 */

public class Data {
	
	/**
	 * Checks if Minecraft is running in a deobfuscated enviroment.
	 */
	
	public static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	
	/**
	 * Checks if the server is running Bukkit.
	 * @return boolean
	 */
	
	public static final boolean isBukkit() {
		try {
			return Class.forName("org.bukkit.Bukkit") != null;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * Checks if the mod is running on the correct MC version.
	 * @return boolean
	 */
	
	public static final boolean wrongVersion() {
		return !Loader.MC_VERSION.equals(mcversion);
	}
	
	/**
	 * Checks if the mod is running on a client.
	 * @return boolean
	 */
	
	public static final boolean isClient() {
		return FMLCommonHandler.instance().getSide().equals(Side.CLIENT);
	}
	
	/**
	 * Checks if Minecraft is using Java 8.
	 * @return boolean
	 */
	
	public static final boolean isJava8() {
		return System.getProperty("java.version").contains("1.8.0_");
	}
	
	/**
	 * The modid. DO NOT MODIFY!
	 */
	
	public static final String modid = "SquidUtils";
	
	/**
	 * The mod name.
	 */
	
	public static final String name = "SquidUtils";
	
	/**
	 * The version.
	 */
	
	public static final String version = "1.1.4";
	
	/**
	 * The MC version to use this mod with.
	 */
	
	public static final String mcversion = "1.7.10";
	
	/**
	 * Link to the MCF thread.
	 */
	
	public static final String forum = "http://bit.ly/squidutilsforum";
	
	/**
	 * Name of the mod author.
	 */
	
	public static final String author = "CoolSquid";
	
	/**
	 * List of dependencies.
	 */
	
	public static final String dependencies = "after:AppleCore";
}