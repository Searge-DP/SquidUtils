package com.github.coolsquid.squidutils.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.github.coolsquid.squidlib.logging.Level;
import com.github.coolsquid.squidlib.logging.Logger;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class EventLogger {
	
	private static final Logger logger = new Logger("logs/EventLogs/", "");
	
	/**
	 * Logs all blocks broken.
	 */
	
	@SubscribeEvent
	public void blockBreak(BreakEvent event) {
		String s = " ";
		String pos = event.x + s + event.y + s + event.z;
		String m = "\"";
		logger.log("BlockBroken", Level.INFO, m + event.block.getLocalizedName()+ m + " was broken by: " + m + event.getPlayer().getDisplayName() + m + " at: " + pos, false);
	}
	
	/**
	 * Logs all entities killed.
	 */
	
	@SubscribeEvent
	public void kill(LivingDeathEvent event) {
		Entity source = (Entity) event.source.getEntity();
		String m = "\"";
		if (source instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			logger.log("EntityDeath", Level.INFO, m + player.getDisplayName() + m + " killed " + event.entity, false);
		}
		
		else if (!(source instanceof EntityPlayer) && source instanceof Entity) {
			logger.log("EntityDeath", Level.INFO, m + source + m + " killed " + event.entity, false);
		}
		
		else if (!(event.source.getEntity() instanceof Entity) && !(event.source.damageType.equals("generic"))) {
			logger.log("EntityDeath", Level.INFO, m + event.source.getDamageType() + m + " killed " + event.entity, false);
		}
	}
	
	/**
	 * Saves the log to minecraft/logs/EventLogs
	 * @param event
	 */
	
	@SubscribeEvent
	public void save(WorldEvent.Save event) {
		logger.save();
	}
}
