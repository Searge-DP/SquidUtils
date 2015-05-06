/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;

import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidutils.api.IMCHandler;
import coolsquid.squidutils.util.EventHandlerManager;

public class CommonHandler {

	private final Set<DamageSource> disabledDamageSources = Sets.newHashSet();
	private final Set<String> bannedItems = Sets.newHashSet();
	private final Set<String> disabledCommands = new HashSet<String>();
	private final Set<Block> physics = Sets.newHashSet();
	private final Set<Character> allowedChars = Sets.newHashSet();
	private final Set<ElementType> disabledOverlays = Sets.newHashSet();
	private final ArrayListMultimap<Item, String> tooltips = ArrayListMultimap.create();
	private final EventHandlerManager eventHandlerManager = EventHandlerManager.create();
	private final IMCHandler imc = new IMCHandler();
	private boolean debug;

	public void addTooltip(Item item, String tooltip) {
		this.tooltips.put(item, tooltip);
	}

	public void addTooltips(Item item, Iterable<String> tooltips) {
		for (String tooltip: tooltips) {
			this.tooltips.put(item, tooltip);
		}
	}

	public void banItem(String name) {
		ContentRemover.remove(name, ContentType.RECIPE);
		ContentRemover.remove(name, ContentType.SMELTING);
		ContentRemover.remove(name, ContentType.FISH);
		ContentRemover.remove(name, ContentType.JUNK);
		ContentRemover.remove(name, ContentType.TREASURE);
		this.bannedItems.add(name);
	}

	public void disableDamageSource(DamageSource source) {
		this.disabledDamageSources.add(source);
	}

	public void disableCommand(String name) {
		this.disabledCommands.add(name);
	}

	public void addAllowedChar(char c) {
		this.allowedChars.add(c);
	}

	public void activatePhysicsForBlock(BlockFalling block) {
		this.physics.add(block);
	}

	public Set<DamageSource> getDisabledDamageSources() {
		return this.disabledDamageSources;
	}

	public Set<String> getBannedItems() {
		return this.bannedItems;
	}

	public Set<String> getDisabledCommands() {
		return this.disabledCommands;
	}

	public ArrayListMultimap<Item, String> getTooltips() {
		return this.tooltips;
	}

	public Set<Block> getPhysics() {
		return this.physics;
	}

	public Set<Character> getAllowedChars() {
		return this.allowedChars;
	}

	public EventHandlerManager getEventHandlerManager() {
		return this.eventHandlerManager;
	}

	public boolean isDebugMode() {
		return this.debug;
	}

	public void setDebugMode(boolean debug) {
		this.debug = debug;
	}

	public IMCHandler getIMCHandler() {
		return this.imc;
	}

	public void disableOverlay(ElementType overlay) {
		this.disabledOverlays.add(overlay);
	}

	public Set<ElementType> getDisabledOverlays() {
		return this.disabledOverlays;
	}
}