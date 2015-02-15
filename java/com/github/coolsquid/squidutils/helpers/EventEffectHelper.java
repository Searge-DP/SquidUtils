/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.Explosion;

import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.util.EffectInfo;
import com.github.coolsquid.squidutils.util.EventInfo;

public class EventEffectHelper {
	
	public static void run(HashMap<Object, EventInfo> items, Item item, EntityLivingBase entity) {
		if (items.containsKey(item)) {
			performEffects(items.get(item), entity);
		}
	}
	
	public static void performEffects(EventInfo info, EntityLivingBase entity) {
		if (info.getMinHealth() >= entity.getHealth() || info.getMaxHealth() <= entity.getHealth()) return;
		if (info.getMinarmor() >= entity.getTotalArmorValue() || info.getMaxarmor() <= entity.getTotalArmorValue()) return;
		LogHelper.info("debug2");
		float size = info.getExplosionsize();
		if (info.getExplosionsize() > 0) {
			LogHelper.info("debug3");
			if (Utils.getChance(info.getMinchance(), info.getMaxchance())) {
				LogHelper.info("debug4");
				Explosion e = new Explosion(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, size);
				e.doExplosionA();
				e.doExplosionB(true);
			}
		}
		float dmg = info.getDamageamount();
		if (dmg > 0) {
			if (Utils.getChance(info.getMinchance(), info.getMaxchance())) {
				entity.setHealth(entity.getHealth() - dmg);
			}
		}
		ArrayList<EffectInfo> effects = info.getEffects();
		if (!effects.isEmpty()) {
			if (Utils.getChance(info.getMinchance(), info.getMaxchance())) {
				for (int b = 0; b < effects.size(); b++) {
					EffectInfo effectinfo = effects.get(b);
					PotionEffect effect = new PotionEffect(effectinfo.getId(), effectinfo.getDuration(), effectinfo.getAmplifier());
					entity.addPotionEffect(effect);
				}
			}
		}
		int exp = info.getExperienceamount();
		if (exp > 0 && entity instanceof EntityPlayer) {
			if (Utils.getChance(info.getMinchance(), info.getMaxchance())) {
				((EntityPlayer) entity).addExperience(exp);
			}
		}
	}
}