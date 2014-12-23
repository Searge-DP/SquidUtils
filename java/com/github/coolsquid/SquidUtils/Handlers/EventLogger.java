package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;
import com.github.coolsquid.SquidUtils.Utils.Logging.Logger;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class EventLogger {
		
	@SubscribeEvent
	public void BlockBreak(BreakEvent event) {
		String s = " ";
		String pos = event.x + s + event.y + s + event.z;
		String m = "\"";
		LogHelper.info((m + event.block.getLocalizedName() + m + " was broken by: " + m + event.getPlayer().getDisplayName()) + m + " at: " + pos);
		Logger.log("BlockBroken", "INFO", m + event.block.getLocalizedName()+ m + " was broken by: " + m + event.getPlayer().getDisplayName() + m + " at: " + pos);
	}
	
	@SubscribeEvent
	public void Kill(LivingDeathEvent event) {
		Entity source = (Entity) event.source.getEntity();
		String m = "\"";
		if (source instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			LogHelper.info(m + player.getDisplayName() + m + " killed " + event.entity);
			Logger.log("EntityDeath", "INFO", m + player.getDisplayName() + m + " killed " + event.entity);
		}
		
		else if (!(source instanceof EntityPlayer) && source instanceof Entity) {
			LogHelper.info(m + source + m + " killed " + event.entity);
			Logger.log("EntityDeath", "INFO", m + source + m + " killed " + event.entity);
		}
		
		else if (!(event.source.getEntity() instanceof Entity) && !(event.source.damageType.equals("generic"))) {
			LogHelper.info(m + event.source.getDamageType() + m + " killed " + event.entity);
			Logger.log("EntityDeath", "INFO", m + event.source.getDamageType() + m + " killed " + event.entity);
		}
	}
	
	@SubscribeEvent
	public void tick(WorldEvent.Save event) {
		Logger.save();
	}
}