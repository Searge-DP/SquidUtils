/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	private String entitytype = "";
	
	private String action = "";
	
	private final HashMap<Object, EventInfo> keytoinfo = new HashMap<Object, EventInfo>();
	
	public void addKey(Object key, EventInfo info) {
		this.keytoinfo.put(key, info);
	}

	public HashMap<Object, EventInfo> getKeytoinfo() {
		return this.keytoinfo;
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