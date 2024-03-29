/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.impl;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.collect.CollectUtils;
import coolsquid.squidapi.util.collect.Registry;
import coolsquid.squidapi.util.collect.impl.RegistryImpl;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.api.ScriptingAPI;
import coolsquid.squidutils.api.SquidUtilsAPI;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

/**
 * @author CoolSquid
 * @see coolsquid.squidutils.api.SquidUtilsAPI
 */
public class SquidUtilsAPIImpl implements SquidUtilsAPI {

	final Registry<DamageSource> damageSources = new RegistryImpl<DamageSource>();
	final Registry<Material> materials = new RegistryImpl<Material>();
	final Registry<SoundType> soundTypes = new RegistryImpl<SoundType>();

	private ScriptingAPI scripting;

	public SquidUtilsAPIImpl() {
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

		this.soundTypes.register("stone", Block.soundTypeStone);
		this.soundTypes.register("wood", Block.soundTypeWood);
		this.soundTypes.register("gravel", Block.soundTypeGravel);
		this.soundTypes.register("grass", Block.soundTypeGrass);
		this.soundTypes.register("piston", Block.soundTypePiston);
		this.soundTypes.register("metal", Block.soundTypeMetal);
		this.soundTypes.register("glass", Block.soundTypeGlass);
		this.soundTypes.register("cloth", Block.soundTypeCloth);
		this.soundTypes.register("sand", Block.soundTypeSand);
		this.soundTypes.register("snow", Block.soundTypeSnow);
		this.soundTypes.register("ladder", Block.soundTypeLadder);
		this.soundTypes.register("anvil", Block.soundTypeAnvil);

		if (SquidUtils.API != null) {
			throw new UnsupportedOperationException("Use the existing instance >.<");
		}
	}

	@Override
	public void registerDamageSource(DamageSource source) {
		ModContainer mod = Loader.instance().activeModContainer();
		if (mod == null) {
			throw new IllegalStateException(new Throwable().getStackTrace()[0].getClassName() + " called an API method outside the lifecycle events");
		}
		Utils.checkNotNull(source, source.damageType);
		this.damageSources.register(mod.getModId() + ':' + source.damageType, source);
	}

	@Override
	public void registerMaterial(String name, Material material) {
		ModContainer mod = Loader.instance().activeModContainer();
		if (mod == null) {
			throw new IllegalStateException(new Throwable().getStackTrace()[0].getClassName() + " called an API method outside the lifecycle events");
		}
		Utils.checkNotNull(name);
		this.materials.register(mod.getModId() + ':' + name, material);
	}

	@Override
	public void registerSoundType(String name, SoundType soundType) {
		ModContainer mod = Loader.instance().activeModContainer();
		if (mod == null) {
			throw new IllegalStateException(new Throwable().getStackTrace()[0].getClassName() + " called an API method outside the lifecycle events");
		}
		Utils.checkNotNull(name);
		this.soundTypes.register(mod.getModId() + ':' + name, soundType);
	}

	@Override
	public Registry<DamageSource> getDamageSources() {
		return CollectUtils.immutableRegistry(this.damageSources);
	}

	@Override
	public Registry<Material> getMaterials() {
		return CollectUtils.immutableRegistry(this.materials);
	}

	@Override
	public Registry<SoundType> getSoundTypes() {
		return CollectUtils.immutableRegistry(this.soundTypes);
	}

	@Override
	public ScriptingAPI getScripting() {
		return this.scripting;
	}

	public void setScripting(ScriptingAPI scripting) {
		Utils.checkNotNull(scripting);
		this.scripting = scripting;
	}
}