/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import net.minecraft.launchwrapper.Launch;

public class Names {

	public static final boolean DEV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final String DESC = DEV ? "(F)Lnet/minecraft/block/Block;" : "(F)Laji;";

	public static final String SET_HARDNESS = DEV ? "setHardness" : "c";
	public static final String SET_RESISTANCE = DEV ? "setResistance" : "b";
	public static final String SET_LIGHTLEVEL = DEV ? "setLightLevel" : "a";

	public static final String BLOCK_FALLING_UPDATE = DEV ? "updateTick" : "a";
	public static final String BLOCK_FALLING_UPDATE_DESC = DEV ? "(Lnet/minecraft/world/World;IIILjava/util/Random;)V" : "(Lahb;IIILjava/util/Random;)V";

	public static final String BLOCK_PORTAL_TP = DEV ? "onEntityCollidedWithBlock" : "a";
	public static final String BLOCK_PORTAL_TP_DESC = DEV ? "(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V" : "(Lahb;IIILsa;)V";
}