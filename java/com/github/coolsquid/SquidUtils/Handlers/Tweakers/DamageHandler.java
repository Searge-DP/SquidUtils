package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.github.coolsquid.SquidUtils.Utils.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class DamageHandler {
	
	@SubscribeEvent
	public final void event(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			if (Utils.getChance(1, 10000)) {
				event.entityLiving.setHealth(event.entityLiving.getHealth() / 3 * 2);
			}
		}
		else {
			if (Utils.getChance(1, 10000) && event.entity instanceof EntityAnimal && !(event.entityLiving.isChild())) {
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.harm.id, 255));
			}
		}
	}
}