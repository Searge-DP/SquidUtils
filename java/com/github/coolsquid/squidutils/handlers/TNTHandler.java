/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TNTHandler {
		
	@SubscribeEvent
	public final void event(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityMinecartTNT) {
			event.setCanceled(true);
			if (ConfigHandler.tntDropItems) {
				event.entity.entityDropItem(new ItemStack(Items.tnt_minecart, 1), 0);
			}
			LogHelper.debug("Minecart with TNT blocked.");
		}
		else if(event.entity instanceof EntityTNTPrimed) {
			event.setCanceled(true);
			if (ConfigHandler.tntDropItems) {
				event.entity.entityDropItem(new ItemStack(Blocks.tnt, 1), 0);
			}
			LogHelper.debug("TNT blocked.");
		}
	}
}