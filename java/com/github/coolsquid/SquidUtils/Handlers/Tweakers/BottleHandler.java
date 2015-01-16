package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.github.coolsquid.SquidUtils.Item.IGlassBottle;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("deprecation")
public class BottleHandler {
	
	@SubscribeEvent
	public void event(PlayerInteractEvent event) {
		World world = event.world;
		if ((event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) && event.entityPlayer.getHeldItem().getItem().getUnlocalizedName().equals(Items.glass_bottle)) {
			try {
				if (event.action == Action.RIGHT_CLICK_BLOCK) {
					event.setCanceled(true);
					return;
				}
				MovingObjectPosition pos = new IGlassBottle().blockCheck(world, event.entityPlayer);
				int x = pos.blockX;
				int y = pos.blockY;
				int z = pos.blockZ;
				Block b = world.getBlock(x, y, z);
				if (b != Blocks.water && b != Blocks.air) {
					event.setResult(Result.DENY);
				}
			} catch (NullPointerException e) {
				
			}
		}
	}
}