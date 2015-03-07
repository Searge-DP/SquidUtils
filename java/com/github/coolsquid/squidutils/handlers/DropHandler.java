/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.github.coolsquid.squidapi.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DropHandler {
	
	public static final HashMap<Block, ArrayList<Drop>> drops = new HashMap<Block, ArrayList<Drop>>();
	public static final HashMap<Block, HashSet<Item>> dropstoremove = new HashMap<Block, HashSet<Item>>();
	public static final HashSet<Block> shouldclear = new HashSet<Block>();
	
	public static void removeDrop(Block block, Item item) {
		if (!dropstoremove.containsKey(block)) {
			dropstoremove.put(block, Sets.newHashSet());
		}
		dropstoremove.get(block).add(item);
	}
	
	public static void addDrop(Block block, Drop drop) {
		if (!drops.containsKey(block)) {
			drops.put(block, Lists.newArrayList());
		}
		drops.get(block).add(drop);
	}
	
	@SubscribeEvent
	public void onDrop(HarvestDropsEvent event) {
		if (shouldclear.contains(event.block)) {
			event.drops.clear();
		}
		if (dropstoremove.containsKey(event.block)) {
			for (int a = 0; a < event.drops.size(); a++) {
				if (dropstoremove.get(event.block).contains(event.drops.get(a).getItem())) {
					event.drops.remove(a);
				}
			}
		}
		if (drops.containsKey(event.block)) {
			for (Drop a: drops.get(event.block)) {
				if (a.getChance()) {
					event.drops.add(new ItemStack(a.getItem(), a.getAmount()));
				}
			}
		}
	}
	
	public static class Drop {
		private final Item item;
		private final int minamount;
		private final int maxamount;
		private final Chance chance;
		
		public Drop(Item item, int minamount, int maxamount, Chance chance) {
			this.item = item;
			this.minamount = minamount;
			this.maxamount = maxamount;
			this.chance = chance;
		}
		
		public Item getItem() {
			return this.item;
		}
		
		public int getMinamount() {
			return this.minamount;
		}
		
		public int getMaxamount() {
			return this.maxamount;
		}
		
		public boolean getChance() {
			return this.chance.getChance();
		}
		
		public int getAmount() {
			return Utils.getRandInt(this.minamount, this.maxamount);
		}
	}
	
	public static class Chance {
		private final int min;
		private final int max;
		
		public Chance(int min, int max) {
			this.min = min;
			this.max = max;
		}
		
		public boolean getChance() {
			return Utils.getChance(this.min, this.max);
		}
	}
}