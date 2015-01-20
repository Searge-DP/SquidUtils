package com.github.coolsquid.squidutils.handlers;

import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BottleHandler {
	
	@SubscribeEvent
	public void event(PlayerInteractEvent event) {
		if (event.action != Action.LEFT_CLICK_BLOCK && event.entityPlayer.getHeldItem().getItem() != null) {
			if (event.entityPlayer.getHeldItem().getItem() == Items.glass_bottle) {
				event.setCanceled(true);
			}
		}
	}
}