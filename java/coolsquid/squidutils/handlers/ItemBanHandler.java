/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemBanHandler {

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if (event.entityPlayer.getHeldItem() != null && SquidUtils.API.getBannedItems().contains(Item.itemRegistry.getNameForObject(event.entityPlayer.getHeldItem().getItem()))) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onItemJoin(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityItem) {
			EntityItem entityitem = (EntityItem) event.entity;
			if (SquidUtils.API.getBannedItems().contains(Item.itemRegistry.getNameForObject(entityitem.getEntityItem().getItem()))) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockPlaced(PlaceEvent event) {
		if (SquidUtils.API.getBannedItems().contains(Item.itemRegistry.getNameForObject(event.block))) {
			event.setCanceled(true);
		}
	}
}