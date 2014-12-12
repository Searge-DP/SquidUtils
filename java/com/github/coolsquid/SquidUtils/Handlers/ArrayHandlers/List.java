package com.github.coolsquid.SquidUtils.Handlers.ArrayHandlers;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class List {
	
	public static final ArrayList<Item> LIST = new ArrayList<Item>();

	public static boolean contains(Item item) {
		return LIST.contains(item);
	}
	
	public static void addItem(Item item) {
		if (item != null) {
			LIST.add(item);
		}
		else {
			throw new RuntimeException("\"item\" can't be null!");
		}
	}
	
	public static void addBlock(Block block) {
		if (block != null) {
			LIST.add(Item.getItemFromBlock(block));
		}
		else {
			throw new RuntimeException("\"item\" can't be null!");
		}
	}
	
	public static void remove(Item item) {
		if (item != null) {
			LIST.remove(item);
		}
		else {
			throw new RuntimeException("\"item\" can't be null!");
		}
	}
	
	public static void set(int index, Item item) {
		if (item != null) {
			LIST.set(index, item);
		}
		else {
			throw new RuntimeException("\"item\" can't be null!");
		}
	}
	
	public static boolean isEmpty(Item item) {
		return LIST.isEmpty();
	}
	
	public static void get(int index) {
		LIST.get(index);
	}
	
	public static void size(int index) {
		LIST.size();
	}
}