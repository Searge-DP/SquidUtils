package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class EventLogger {
	
	int A = 1;
	
	@SubscribeEvent
	public void BlockBreak(BreakEvent event) {
		String s = " ";
		String pos = event.x + s + event.y + s + event.z;
		String m = "\"";
		event.setResult(Result.ALLOW);
		LogHandler.info((m + event.block.getLocalizedName()+ m + " was broken by: " + m + event.getPlayer().getDisplayName()) + m + " at: " + pos);
	}
	
	@SubscribeEvent
	public void Kill(LivingDeathEvent event) {
		Entity source = (Entity) event.source.getEntity();
		String m = "\"";
		if (source instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			LogHandler.info(m + player.getDisplayName() + m + " killed " + event.entity);
		}
		
		else if (!(source instanceof EntityPlayer) && source instanceof Entity) {
			LogHandler.info(m + source + m + " killed " + event.entity);
		}
		
		else if (!(event.source.getEntity() instanceof Entity) && !(event.source.damageType.equals("generic"))) {
			LogHandler.info(m + event.source.getDamageType() + m + " killed " + event.entity);
		}
	}
}