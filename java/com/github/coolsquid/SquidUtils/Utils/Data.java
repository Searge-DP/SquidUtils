package com.github.coolsquid.SquidUtils.Utils;

import net.minecraft.launchwrapper.Launch;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Data {
	
	public static final boolean developmentEnvironment = (Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");
	
	public static final String modid = "SquidUtils";
	public static final String version = "1.1.1";
	public static final String forum = "http://bit.ly/squidutilsforum";
	public static final String author = "CoolSquid";
}