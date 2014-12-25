package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class TNTHandler {
	
	@SubscribeEvent
	public final void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityMinecartTNT) {
			event.setCanceled(true);
			if (ConfigHandler.TNTDropItems) {
				event.entity.entityDropItem(new ItemStack(Items.tnt_minecart, 1), 0);
			}
		}
		else if(event.entity instanceof EntityTNTPrimed) {
			event.setCanceled(true);
			if (ConfigHandler.TNTDropItems) {
				event.entity.entityDropItem(new ItemStack(Blocks.tnt, 1), 0);
			}
		}
		LogHelper.debug("TNT blocked.");
	}
}
