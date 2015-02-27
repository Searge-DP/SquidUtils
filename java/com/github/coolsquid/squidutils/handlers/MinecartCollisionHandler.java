/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;

import com.github.coolsquid.squidapi.reflection.ReflectionHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MinecartCollisionHandler {
	
	@SubscribeEvent
	public void onCollide(MinecartCollisionEvent event) {
		if (event.minecart instanceof EntityMinecartTNT) {
			ReflectionHelper.in(EntityMinecartTNT.class).field("minecartTNTFuse", "field_94106_a").set(event.minecart, 5);
		}
	}
}