/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;
import coolsquid.squidapi.registry.Registry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class SquidUtilsAPI {

	private final Registry<DamageSource> damageSources = Registry.create();
	private final Registry<Material> materials = Registry.create();

	private final ScriptingAPI scripting = new ScriptingAPI();
	private final IMCHandler imc = new IMCHandler();

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
		ModContainer mod = Loader.instance().activeModContainer();
		if (mod == null) {
			throw new IllegalStateException();
		}
		this.damageSources.register(mod.getModId() + ':' + source.damageType, source);
	}

	public void registerMaterial(String name, Material material) {
		ModContainer mod = Loader.instance().activeModContainer();
		if (mod == null) {
			throw new IllegalStateException();
		}
		this.materials.register(mod.getModId() + ':' + name, material);
	}

	public Registry<DamageSource> getDamageSources() {
		return this.damageSources;
	}

	public Registry<Material> getMaterials() {
		return this.materials;
	}

	public ScriptingAPI getScripting() {
		return this.scripting;
	}

	void registerDamageSourceWithIMC(String mod, DamageSource source) {
		this.damageSources.register(mod + ':' + source.damageType, source);
	}

	void registerMaterialWithIMC(String mod, String name, Material material) {
		this.materials.register(mod + ':' + name, material);
	}

	public IMCHandler getIMCHandler() {
		return this.imc;
	}
}