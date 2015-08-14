/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.io.File;
import java.util.BitSet;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;

import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.collect.Registry;
import coolsquid.squidapi.util.collect.impl.RegistryImpl;
import coolsquid.squidutils.api.impl.IMCHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CommonHandler {

	private final Set<DamageSource> disabledDamageSources = Sets.newHashSet();
	private final Set<Item> bannedItems = Utils.identityHashSet();
	private final Set<String> disabledCommands = new HashSet<String>();
	private final Set<Block> physics = Utils.identityHashSet();
	private final BitSet allowedChars = new BitSet();
	private final Set<ElementType> disabledOverlays = Sets.newHashSet();
	private final Set<Item> undroppableItems = Utils.identityHashSet();
	private final Set<CreativeTabs> tabsWithSearch = Utils.identityHashSet();
	private final Map<Item, Integer> fuels = new IdentityHashMap<Item, Integer>();
	private final Registry<CreativeTabs> creativeTabs = new RegistryImpl<CreativeTabs>();
	private final ArrayListMultimap<Item, String> tooltips = ArrayListMultimap.create();
	private final IMCHandler imc = new IMCHandler();
	private boolean debug;
	private final File config_dir = new File("./config/SquidUtils");

	public void addTooltip(Item item, String tooltip) {
		this.tooltips.put(item, tooltip);
	}

	public void addTooltips(Item item, Iterable<String> tooltips) {
		for (String tooltip: tooltips) {
			this.tooltips.put(item, tooltip);
		}
	}

	public void banItem(Item item) {
		String name = Item.itemRegistry.getNameForObject(item);
		ContentRemover.remove(name, ContentType.RECIPE);
		ContentRemover.remove(name, ContentType.SMELTING);
		ContentRemover.remove(name, ContentType.FISH);
		ContentRemover.remove(name, ContentType.JUNK);
		ContentRemover.remove(name, ContentType.TREASURE);
		this.bannedItems.add(item);
	}

	public void disableDamageSource(DamageSource source) {
		this.disabledDamageSources.add(source);
	}

	public void disableCommand(String name) {
		this.disabledCommands.add(name);
	}

	public void addAllowedChar(char c) {
		this.allowedChars.set(c);
	}

	public boolean isAllowedChar(char c) {
		return this.allowedChars.get(c);
	}

	public void activatePhysicsForBlock(BlockFalling block) {
		this.physics.add(block);
	}

	public Set<DamageSource> getDisabledDamageSources() {
		return this.disabledDamageSources;
	}

	public boolean isBanned(Item item) {
		return this.bannedItems.contains(item);
	}

	public Set<String> getDisabledCommands() {
		return this.disabledCommands;
	}

	public ArrayListMultimap<Item, String> getTooltips() {
		return this.tooltips;
	}

	public BitSet getAllowedChars() {
		return this.allowedChars;
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
		if (this.disabledOverlays.isEmpty()) {
			MinecraftForge.EVENT_BUS.register(new GameOverlayHandler());
		}
		this.disabledOverlays.add(overlay);
	}

	public Set<ElementType> getDisabledOverlays() {
		return this.disabledOverlays;
	}

	@SideOnly(Side.CLIENT)
	public void registerCreativeTabs() {
		for (CreativeTabs tab: CreativeTabs.creativeTabArray) {
			if (!this.creativeTabs.containsValue(tab) && !this.creativeTabs.containsName(tab.getTabLabel())) {
				this.creativeTabs.register(tab.getTabLabel(), tab);
			}
		}
	}

	public Registry<CreativeTabs> getCreativeTabs() {
		return this.creativeTabs;
	}

	public void registerFuel(Item item, int burnTime) {
		this.fuels.put(item, burnTime);
	}

	public void finishedLoading() {
		if (MiscLib.CLIENT) {
			this.registerCreativeTabs();
		}
		if (!this.fuels.isEmpty()) {
			GameRegistry.registerFuelHandler(new IFuelHandler() {
				@Override
				public int getBurnTime(ItemStack fuel) {
					return CommonHandler.this.fuels.containsKey(fuel.getItem()) ? CommonHandler.this.fuels.get(fuel.getItem()) : 0;
				}
			});
		}
	}

	public void setUndroppable(Item item) {
		this.undroppableItems.add(item);
	}

	public Set<Item> getUndroppableItems() {
		return this.undroppableItems;
	}

	public Map<Item, Integer> getFuels() {
		return this.fuels;
	}

	public File getConfigDirectory() {
		return this.config_dir;
	}

	public File getConfigFile(String name) {
		return new File(this.getConfigDirectory(), name);
	}

	public boolean hasPhysics(BlockFalling block) {
		return this.physics.contains(block);
	}

	public Set<Item> getBannedItems() {
		return this.bannedItems;
	}

	public void setHasSearchBar(CreativeTabs c) {
		this.tabsWithSearch.add(c);
	}

	public boolean hasSearchBar(CreativeTabs c) {
		return this.tabsWithSearch.contains(c);
	}
}