package com.github.coolsquid.SquidUtils.Utils;

import net.minecraft.client.Minecraft;
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
	
	public static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	public static final boolean isBukkit() {
		try {
			return Class.forName("org.bukkit.Bukkit") != null;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	public static final boolean wrongVersion() {
		return !Loader.MC_VERSION.equals("1.7.10");
	}
	
	public static final boolean isClient() {
		return FMLCommonHandler.instance().getSide().equals(Side.CLIENT);
	}
	
	public static final boolean isJava64bit() {
		return Minecraft.getMinecraft().isJava64bit();
	}
	
	public static final boolean isOs64bit() {
		return System.getProperty("os.arch").contains("64");
	}
	
	public static final boolean isJava8() {
		return System.getProperty("java.version").contains("1.8.0_");
	}
	
	public static final String modid = "SquidUtils";
	public static final String name = "§bSquidUtils";
	public static final String version = "1.1.4";
	public static final String forum = "http://bit.ly/squidutilsforum";
	public static final String author = "CoolSquid";
	public static final String dependencies = "before:OpenBlocks,before:FighterMobs";
}