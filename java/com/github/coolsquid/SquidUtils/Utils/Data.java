package com.github.coolsquid.SquidUtils.Utils;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Data {
	
	public static final boolean developmentEnvironment = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
	
	public static final boolean wV() {
		return !Loader.MC_VERSION.equals("1.7.10");
	}
	
	public static final boolean isJava64bit() {
		return Minecraft.getMinecraft().isJava64bit();
	}
	
	public static final boolean isOs64bit() {
		return System.getProperty("os.arch").contains("64");
	}
	
	public static final String modid = "SquidUtils";
	public static final String version = "1.1.1";
	public static final String forum = "http://bit.ly/squidutilsforum";
	public static final String author = "CoolSquid";
}