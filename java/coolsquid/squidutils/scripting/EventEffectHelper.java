/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import coolsquid.squidapi.util.EffectInfo;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.handlers.DifficultyHandler;
import coolsquid.squidutils.util.EventInfo;

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
		if (!isCorrectType(entity, (String) info.values.get("entitytype"))) {
			return;
		}
		if (((float) info.values.get("minhealth")) >= entity.getHealth() || ((float) info.values.get("maxhealth")) <= entity.getHealth()) {
			return;
		}
		if (((int) info.values.get("minarmor")) >= entity.getTotalArmorValue() || ((int) info.values.get("maxarmor")) <= entity.getTotalArmorValue()) {
			return;
		}
		if (SquidUtils.API.getScripting().getActions().containsKey(info.values.get("action"))) {
			SquidUtils.API.getScripting().getActions().get(info.values.get("action")).run(entity, info);
		}
		performEffects(info);
		if (info.values.get("action").equals("cleareffects")) {
			entity.clearActivePotions();
		}
		if (info.values.get("action").equals("explode")) {
			float size = (float) info.values.get("size");
			Explosion e = new Explosion(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, size);
			e.doExplosionA();
			e.doExplosionB(true);
		}
		if (info.values.get("action").equals("damage")) {
			float dmg = (float) info.values.get("damageamount");
			entity.setHealth(entity.getHealth() - dmg);
		}
		if (info.values.get("action").equals("applyeffect")) {
			EffectInfo effectinfo = (EffectInfo) info.values.get("effect");
			PotionEffect effect = new PotionEffect(effectinfo.getId(), effectinfo.getDuration(), effectinfo.getAmplifier());
			entity.addPotionEffect(effect);
		}
		if (info.values.get("action").equals("setsprinting")) {
			entity.setSprinting(true);
		}
		if (info.values.get("action").equals("setinvisible")) {
			entity.setInvisible(true);
		}
		if (info.values.get("action").equals("placeblock")) {
			entity.worldObj.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, (Block) info.values.get("block"), 0, 2);
		}
		if (info.values.get("action").equals("setburning")) {
			entity.setFire((int) info.values.get("duration"));
		}
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (info.values.get("action").equals("addexperience")) {
				int exp = (int) info.values.get("experience");
				player.addExperience(exp);
			}
			if (info.values.get("action").equals("setfoodlevel")) {
				player.getFoodStats().setFoodLevel((int) info.values.get("foodlevel"));
			}
		}
	}

	public static boolean isCorrectType(EntityLivingBase entity, String type) {
		if (type == null || type.equals("")) {
			return true;
		}
		else if (type.equals("undead") && entity.isEntityUndead()) {
			return true;
		}
		else if (type.equals("animal") && entity instanceof EntityAnimal) {
			return true;
		}
		else {
			return entity.getClass().getName().toLowerCase().contains(type.toLowerCase());
		}
	}

	public static void performEffects(EventInfo info) {
		if (MiscLib.CLIENT && info.values.get("action").equals("setdifficulty")) {
			DifficultyHandler.DifficultyForcer.difficulty = EnumDifficulty.valueOf((String) info.values.get("difficulty"));
		}
	}
}