package com.github.coolsquid.squidutils.handlers;

import java.util.Set;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemBanHandler {
	
	public static final Set<String> bannedItems = Sets.newHashSet();

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if (event.entityPlayer.getHeldItem() != null && bannedItems.contains(Item.itemRegistry.getNameForObject(event.entityPlayer.getHeldItem().getItem()))) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onItemJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityItem) {
			EntityItem entityitem = (EntityItem) event.entity;
			if (bannedItems.contains(Item.itemRegistry.getNameForObject(entityitem.getEntityItem().getItem()))) {
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockPlaced(PlaceEvent event) {
		if (bannedItems.contains(Item.itemRegistry.getNameForObject(event.block))) {
			event.setCanceled(true);
		}
	}
}