/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import coolsquid.squidapi.util.EffectInfo;


public class EntityInfo {

	public int fireResistance;
	public boolean isImmuneToFire;
	public boolean invisible;
	public float absorptionAmount;
	public String name;
	public Set<EffectInfo> effects;
	public Map<Integer, ItemStack> equipment;
	public boolean avoidCats;

	public EntityInfo() {
		this.effects = Sets.newHashSet();
		this.equipment = Maps.newHashMap();
	}
}