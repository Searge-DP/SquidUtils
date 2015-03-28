/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util.script;

public class EffectInfo {
	
	private int id;
	private int duration;
	private int amplifier;
	
	public EffectInfo(int id, int duration, int amplifier) {
		this.id = id;
		this.duration = duration;
		this.amplifier = amplifier;
	}
	
	public int getId() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public int getAmplifier() {
		return amplifier;
	}
}