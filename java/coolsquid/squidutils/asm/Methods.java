/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import net.minecraft.launchwrapper.Launch;

public class Methods {

	public static final boolean DEV_ENVIRONMENT = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final String DESC = DEV_ENVIRONMENT ? "(F)Lnet/minecraft/block/Block;" : "(F)Laji;";

	public static final String SET_HARDNESS = DEV_ENVIRONMENT ? "setHardness" : "c";
	public static final String SET_RESISTANCE = DEV_ENVIRONMENT ? "setResistance" : "b";
	public static final String SET_LIGHTLEVEL = DEV_ENVIRONMENT ? "setLightLevel" : "a";
}