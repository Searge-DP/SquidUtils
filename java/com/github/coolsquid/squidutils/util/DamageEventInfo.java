/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.util;

public class DamageEventInfo {
	
	private float minamount;
	private float maxamount;
	private String dmgtype;

	public void setMinamount(float minamount) {
		this.minamount = minamount;
	}

	public void setMaxamount(float maxamount) {
		this.maxamount = maxamount;
	}

	public void setDmgtype(String dmgtype) {
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