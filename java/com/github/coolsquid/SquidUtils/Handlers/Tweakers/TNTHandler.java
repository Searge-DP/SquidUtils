package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
			if (ConfigHandler.getTNTDropItems()) {
				event.entity.entityDropItem(new ItemStack(Items.tnt_minecart, 1), 0);
			}
		}
		else if(event.entity instanceof EntityTNTPrimed) {
			event.setCanceled(true);
			if (ConfigHandler.getTNTDropItems()) {
				event.entity.entityDropItem(new ItemStack(Blocks.tnt, 1), 0);
			}
		}
		LogHandler.debug("TNT blocked.");
	}
}
