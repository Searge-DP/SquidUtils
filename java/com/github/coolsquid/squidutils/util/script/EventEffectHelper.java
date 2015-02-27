/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util.script;

import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.Explosion;

import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.handlers.DifficultyHandler;
import com.github.coolsquid.squidutils.helpers.PermissionHelper;

public class EventEffectHelper {
	
	public static void run(HashMap<Object, EventInfo> items, Item item, EntityLivingBase entity) {
		if (items.containsKey(item)) {
			performEffects(items.get(item), entity);
		}
	}
	
	public static void performEffects(EventInfo info, EntityLivingBase entity) {
		if (!Utils.getChance(info.getMinchance(), info.getMaxchance())) {
			return;
		}
		if (!isCorrectType(entity, info.getEntitytype())) return;
		if (info.getMinHealth() >= entity.getHealth() || info.getMaxHealth() <= entity.getHealth()) return;
		if (info.getMinarmor() >= entity.getTotalArmorValue() || info.getMaxarmor() <= entity.getTotalArmorValue()) return;
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (!info.getRequiredperm().equals("") && !PermissionHelper.hasPermission(player.getGameProfile().getId(), info.getRequiredperm())) {
				return;
			}
			if (!info.getOppositeperm().equals("") && PermissionHelper.hasPermission(player.getGameProfile().getId(), info.getOppositeperm())) {
				return;
			}
		}
		if (ScriptingAPI.getActions().containsKey(info.getAction())) {
			ScriptingAPI.getActions().get(info.getAction()).run(entity, info);
		}
		performEffects(info);
		float size = info.getExplosionsize();
		if (info.clearActiveEffects()) {
			entity.clearActivePotions();
		}
		if (info.getExplosionsize() > 0) {
			Explosion e = new Explosion(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, size);
			e.doExplosionA();
			e.doExplosionB(true);
		}
		float dmg = info.getDamageamount();
		if (dmg > 0) {
			entity.setHealth(entity.getHealth() - dmg);
		}
		EffectInfo effectinfo = info.getEffect();
		if (effectinfo != null) {
			PotionEffect effect = new PotionEffect(effectinfo.getId(), effectinfo.getDuration(), effectinfo.getAmplifier());
			entity.addPotionEffect(effect);
		}
		if (info.sprint()) {
			entity.setSprinting(true);
		}
		if (info.invisible()) {
			entity.setInvisible(true);
		}
		if (info.getBlocktoplace() != null) {
			entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, info.getBlocktoplace(), 0, 2);
		}
		if (info.getFireamount() != 0) {
			entity.setFire(info.getFireamount());
		}
		int exp = info.getExperienceamount();
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (exp > 0) {
				player.addExperience(exp);
			}
			if (info.getFoodlevel() != -1) {
				player.getFoodStats().setFoodLevel(info.getFoodlevel());
			}
		}
	}
	
	public static boolean isCorrectType(EntityLivingBase entity, String type) {
		if (type.equals(""))
			return true;
		else if (type.equals("undead") && entity.isEntityUndead())
			return true;
		else if (type.equals("animal") && entity instanceof EntityAnimal)
			return true;
		else return entity.getClass().getName().toLowerCase().contains(type.toLowerCase());
	}

	public static void performEffects(EventInfo info) {
		if (!info.getDifficulty().equals("") && Utils.isClient()) {
			DifficultyHandler.difficulty = info.getDifficulty();
		}
	}
}