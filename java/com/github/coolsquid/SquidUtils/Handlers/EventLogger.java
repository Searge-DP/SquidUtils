package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLogger {
	
	@SubscribeEvent
	public final void BlockBreak(BlockEvent.BreakEvent event) {
		String s = " ";
		String pos = event.x + s + event.y + s + event.z;
		String m = "\"";
		LogHandler.info((m + event.block.getLocalizedName()+ m + " was broken by: " + m + event.getPlayer().getDisplayName()) + m + " at: " + pos);
	}
	
	@SubscribeEvent
	public final void LivingDeath(LivingDeathEvent event) {
		EntityPlayer player = (EntityPlayer) event.source.getEntity();
		String m = "\"";
		if (event.source.getEntity() instanceof EntityPlayer) {
			LogHandler.info(m + player.getDisplayName() + m + " killed " + event.entityLiving);
		}
	}
}