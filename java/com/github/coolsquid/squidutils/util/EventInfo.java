/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import java.util.ArrayList;

import net.minecraft.item.Item;

public class EventInfo {
	
	private float explosionsize;
	private float damageamount;
	private ArrayList<EffectInfo> effects = new ArrayList<EffectInfo>();
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

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public boolean addEffect(EffectInfo info) {
		return this.effects.add(info);
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

	public ArrayList<EffectInfo> getEffects() {
		return this.effects;
	}
}