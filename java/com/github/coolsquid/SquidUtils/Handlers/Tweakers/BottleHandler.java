package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.github.coolsquid.SquidUtils.Item.IGlassBottle;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BottleHandler {
	
	@SubscribeEvent
	public void event(PlayerInteractEvent event) {
		World world = event.world;
		if (event.action == Action.RIGHT_CLICK_AIR) {
			IGlassBottle bottle = new IGlassBottle();
			bottle.onItemRightClick(event.entityPlayer.getHeldItem(), world, event.entityPlayer);
		}
		else if (event.action == Action.RIGHT_CLICK_BLOCK) {
			event.setCanceled(true);
		}
	}
}