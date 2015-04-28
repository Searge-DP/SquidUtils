/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Sets;

import coolsquid.squidapi.registry.Registry;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.api.eventhandler.EventHandlerManager;

public class SquidUtilsAPI {

	private final Registry<DamageSource> damageSources = Registry.create();
	private final Registry<Material> materials = Registry.create();

	private final Set<DamageSource> bannedDamageSources = Sets.newHashSet();
	private final Set<String> bannedItems = Sets.newHashSet();
	private final Set<String> disabledCommands = new HashSet<String>();
	private final ArrayListMultimap<Item, String> tooltips = ArrayListMultimap.create();
	private final EventHandlerManager eventHandlerManager = EventHandlerManager.create();
	private final ScriptingAPI scripting = new ScriptingAPI();

	public SquidUtilsAPI() {
		this.damageSources.register("anvil", DamageSource.anvil);
		this.damageSources.register("cactus", DamageSource.cactus);
		this.damageSources.register("drown", DamageSource.drown);
		this.damageSources.register("fall", DamageSource.fall);
		this.damageSources.register("fallingBlock", DamageSource.fallingBlock);
		this.damageSources.register("generic", DamageSource.generic);
		this.damageSources.register("inFire", DamageSource.inFire);
		this.damageSources.register("inWall", DamageSource.inWall);
		this.damageSources.register("lava", DamageSource.lava);
		this.damageSources.register("magic", DamageSource.magic);
		this.damageSources.register("onFire", DamageSource.onFire);
		this.damageSources.register("outOfWorld", DamageSource.outOfWorld);
		this.damageSources.register("starve", DamageSource.starve);
		this.damageSources.register("wither", DamageSource.wither);

		this.materials.register("air", Material.air);
		this.materials.register("grass", Material.grass);
		this.materials.register("ground", Material.ground);
		this.materials.register("wood", Material.wood);
		this.materials.register("rock", Material.rock);
		this.materials.register("iron", Material.iron);
		this.materials.register("anvil", Material.anvil);
		this.materials.register("water", Material.water);
		this.materials.register("lava", Material.lava);
		this.materials.register("leaves", Material.leaves);
		this.materials.register("plants", Material.plants);
		this.materials.register("vine", Material.vine);
		this.materials.register("sponge", Material.sponge);
		this.materials.register("cloth", Material.cloth);
		this.materials.register("fire", Material.fire);
		this.materials.register("sand", Material.sand);
		this.materials.register("circuits", Material.circuits);
		this.materials.register("carpet", Material.carpet);
		this.materials.register("glass", Material.glass);
		this.materials.register("redstoneLight", Material.redstoneLight);
		this.materials.register("tnt", Material.tnt);
		this.materials.register("coral", Material.coral);
		this.materials.register("ice", Material.ice);
		this.materials.register("packedIce", Material.packedIce);
		this.materials.register("snow", Material.snow);
		this.materials.register("craftedSnow", Material.craftedSnow);
		this.materials.register("cactus", Material.cactus);
		this.materials.register("clay", Material.clay);
		this.materials.register("gourd", Material.gourd);
		this.materials.register("dragonEgg", Material.dragonEgg);
		this.materials.register("portal", Material.portal);
		this.materials.register("cake", Material.cake);
		this.materials.register("web", Material.web);
		this.materials.register("piston", Material.piston);
	}

	public void registerDamageSource(DamageSource source) {
		this.damageSources.register(Utils.getCurrentMod().getModId() + ":" + source.damageType, source);
	}

	public void registerMaterial(String name, Material material) {
		this.materials.register(Utils.getCurrentMod().getModId() + ":" + name, material);
	}

	public void registerTooltip(Item item, String tooltip) {
		this.tooltips.put(item, tooltip);
	}

	public void registerTooltips(Item item, List<String> tooltips) {
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
		this.bannedDamageSources.add(source);
	}

	public void disableCommand(String name) {
		this.disabledCommands.add(name);
	}

	public Set<String> getBannedItems() {
		return this.bannedItems;
	}

	public Registry<DamageSource> getDamageSources() {
		return this.damageSources;
	}

	public Registry<Material> getMaterials() {
		return this.materials;
	}

	public Set<DamageSource> getDisabledDamageSources() {
		return this.bannedDamageSources;
	}

	public Set<String> getDisabledCommands() {
		return this.disabledCommands;
	}

	public ArrayListMultimap<Item, String> getTooltips() {
		return this.tooltips;
	}

	public EventHandlerManager getEventHandlerManager() {
		return this.eventHandlerManager;
	}

	public ScriptingAPI getScripting() {
		return this.scripting;
	}
}