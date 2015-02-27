/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util.script;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class EventInfo {
	
	private float explosionsize;
	private float damageamount;
	private EffectInfo effect = null;
	private int experienceamount;
	private int maxchance = 1;
	private int minchance = 1;
	private boolean cancel;
	private float minHealth = Float.MIN_VALUE;
	private float maxHealth = Float.MAX_VALUE;
	private int minarmor = Integer.MIN_VALUE;
	private int maxarmor = Integer.MAX_VALUE;
	private float minamount;
	private float maxamount;
	private boolean sprint;
	private boolean invisible;
	private String entitytype = "";
	private String action = "";
	private String damagetype = "";
	private Item item = null;
	private String difficulty = "";
	private String chattrigger = "";
	private String commandname = "";
	private boolean clearActiveEffects;
	private Block blocktoplace = null;
	private int fireamount = 0;
	private int foodlevel = -1;
	private String requiredperm = "";
	private String oppositeperm = "";
	private HashMap<String, String> customvalues = new HashMap<String, String>();

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getCommandname() {
		return this.commandname;
	}

	public void setCommandname(String commandname) {
		this.commandname = commandname;
	}

	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getDamagetype() {
		return this.damagetype;
	}

	public void setDamagetype(String damagetype) {
		this.damagetype = damagetype;
	}

	public float getMinamount() {
		return this.minamount;
	}

	public void setMinamount(float minamount) {
		this.minamount = minamount;
	}

	public float getMaxamount() {
		return this.maxamount;
	}

	public void setMaxamount(float maxamount) {
		this.maxamount = maxamount;
	}

	public boolean invisible() {
		return this.invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public boolean sprint() {
		return this.sprint;
	}

	public void setSprint(boolean sprint) {
		this.sprint = sprint;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getChattrigger() {
		return this.chattrigger;
	}

	public void setChattrigger(String chattrigger) {
		this.chattrigger = chattrigger;
	}

	public String getEntitytype() {
		return this.entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public int getMinarmor() {
		return this.minarmor;
	}

	public void setMinarmor(int minarmor) {
		this.minarmor = minarmor;
	}

	public int getMaxarmor() {
		return this.maxarmor;
	}

	public void setMaxarmor(int maxarmor) {
		this.maxarmor = maxarmor;
	}

	public float getMinHealth() {
		return this.minHealth;
	}

	public void setMinHealth(float minHealth) {
		this.minHealth = minHealth;
	}

	public float getMaxHealth() {
		return this.maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setEffect(EffectInfo info) {
		this.effect = info;
	}

	public float getExplosionsize() {
		return this.explosionsize;
	}

	public void setExplosionsize(float explosionsize) {
		this.explosionsize = explosionsize;
	}

	public float getDamageamount() {
		return this.damageamount;
	}

	public void setDamageamount(float damageamount) {
		this.damageamount = damageamount;
	}

	public int getExperienceamount() {
		return this.experienceamount;
	}

	public void setExperienceamount(int experienceamount) {
		this.experienceamount = experienceamount;
	}

	public int getMaxchance() {
		return this.maxchance;
	}

	public void setMaxchance(int maxchance) {
		this.maxchance = maxchance;
	}

	public int getMinchance() {
		return this.minchance;
	}

	public void setMinchance(int minchance) {
		this.minchance = minchance;
	}

	public boolean shouldCancel() {
		return this.cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public EffectInfo getEffect() {
		return this.effect;
	}

	public boolean clearActiveEffects() {
		return this.clearActiveEffects;
	}
	
	public void setClearActiveEffects(boolean clearActiveEffects) {
		this.clearActiveEffects = clearActiveEffects;
	}

	public Block getBlocktoplace() {
		return this.blocktoplace;
	}

	public void setBlocktoplace(Block blocktoplace) {
		this.blocktoplace = blocktoplace;
	}

	public int getFireamount() {
		return this.fireamount;
	}

	public void setFireamount(int fireamount) {
		this.fireamount = fireamount;
	}

	public int getFoodlevel() {
		return this.foodlevel;
	}

	public void setFoodlevel(int foodlevel) {
		this.foodlevel = foodlevel;
	}

	public String getRequiredperm() {
		return this.requiredperm;
	}

	public void setRequiredperm(String perm) {
		this.requiredperm = perm;
	}

	public String getOppositeperm() {
		return this.oppositeperm;
	}

	public void setOppositeperm(String oppositeperm) {
		this.oppositeperm = oppositeperm;
	}

	public HashMap<String, String> getCustomvalues() {
		return this.customvalues;
	}

	public void addCustomvalue(String key, String value) {
		this.customvalues.put(key, value);
	}
}