/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

public class DamageEventInfo {
	
	private float minamount;
	private float maxamount;
	private String dmgtype;
	
	public DamageEventInfo(float minamount, float maxamount, String dmgtype) {
		this.minamount = minamount;
		this.maxamount = maxamount;
		this.dmgtype = dmgtype;
	}

	public float getMinamount() {
		return minamount;
	}

	public float getMaxamount() {
		return maxamount;
	}
	
	public String getDamageType() {
		return dmgtype;
	}
}