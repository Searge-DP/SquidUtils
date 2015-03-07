/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util.script;

import java.util.HashMap;

import net.minecraft.block.Block;
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
		if (info.values.containsKey("minchance") && info.values.containsKey("maxchance") && !Utils.getChance((int) info.values.get("minchance"), (int) info.values.get("maxchance"))) {
			return;
		}
		if (!isCorrectType(entity, (String) info.values.get("entitytype"))) return;
		if (((float) info.values.get("minhealth")) >= entity.getHealth() || ((float) info.values.get("maxhealth")) <= entity.getHealth()) return;
		if (((int) info.values.get("minarmor")) >= entity.getTotalArmorValue() || ((int) info.values.get("maxarmor")) <= entity.getTotalArmorValue()) return;
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (info.values.containsKey("requiredperm") && !PermissionHelper.hasPermission(player.getGameProfile().getId(), (String) info.values.get("requiredperm"))) {
				return;
			}
			if (info.values.containsKey("oppositeperm") && PermissionHelper.hasPermission(player.getGameProfile().getId(), (String) info.values.get("oppositeperm"))) {
				return;
			}
		}
		if (ScriptingAPI.getActions().containsKey(info.values.get("action"))) {
			ScriptingAPI.getActions().get(info.values.get("action")).run(entity, info);
		}
		performEffects(info);
		if (info.values.containsKey("cleareffects")) {
			entity.clearActivePotions();
		}
		if (info.values.containsKey("size")) {
			float size = (float) info.values.get("size");
			Explosion e = new Explosion(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, size);
			e.doExplosionA();
			e.doExplosionB(true);
		}
		if (info.values.containsKey("damageamount")) {
			float dmg = (float) info.values.get("damageamount");
			entity.setHealth(entity.getHealth() - dmg);
		}
		if (info.values.containsKey("effect")) {
			EffectInfo effectinfo = (EffectInfo) info.values.get("effect");
			PotionEffect effect = new PotionEffect(effectinfo.getId(), effectinfo.getDuration(), effectinfo.getAmplifier());
			entity.addPotionEffect(effect);
		}
		if (info.values.containsKey("sprint")) {
			entity.setSprinting(true);
		}
		if (info.values.containsKey("invisible")) {
			entity.setInvisible(true);
		}
		if (info.values.containsKey("blocktoplace")) {
			entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, (Block) info.values.get("blocktoplace"), 0, 2);
		}
		if (info.values.containsKey("fireduration")) {
			entity.setFire((int) info.values.get("fireduration"));
		}
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (info.values.containsKey("experience")) {
				int exp = (int) info.values.get("experience");
				player.addExperience(exp);
			}
			if (info.values.containsKey("foodlevel") && (int) info.values.get("foodlevel") != -1) {
				player.getFoodStats().setFoodLevel((int) info.values.get("foodlevel"));
			}
		}
	}
	
	public static boolean isCorrectType(EntityLivingBase entity, String type) {
		if (type == null || type.equals(""))
			return true;
		else if (type.equals("undead") && entity.isEntityUndead())
			return true;
		else if (type.equals("animal") && entity instanceof EntityAnimal)
			return true;
		else return entity.getClass().getName().toLowerCase().contains(type.toLowerCase());
	}

	public static void performEffects(EventInfo info) {
		if (info.values.get("difficulty") != null && Utils.isClient()) {
			DifficultyHandler.difficulty = (String) info.values.get("difficulty");
		}
	}
}