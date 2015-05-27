/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;

import com.google.common.annotations.Beta;

import coolsquid.squidapi.util.collect.Registry;

/**
 * Instructions may be found at <a href=http://coolsquidmc.blogspot.no/2015/05/using-squidutils-api.html>my website</a>.
 * @author CoolSquid
 */
public interface SquidUtilsAPI {

	/**
	 * Registers a damage source.
	 * @param source the damagesource to register. Cannot be null.
	 * @throws IllegalArgumentException if the damagesource or source.damageSource is null.
	 */
	public abstract void registerDamageSource(DamageSource source);

	/**
	 * Registers a material.
	 * @param name the name of the material
	 * @param material the material to register
	 * @throws IllegalArgumentException if the name or the material is null.
	 */
	public abstract void registerMaterial(String name, Material material);

	/**
	 * Returns an immutable registry wrapping around the internal damagesource registry
	 * @return the damage sources
	 */
	public abstract Registry<DamageSource> getDamageSources();

	/**
	 * Returns an immutable registry wrapping around the internal material registry.
	 * @return the materials
	 */
	public abstract Registry<Material> getMaterials();

	/**
	 * @return the ScriptingAPI instance.
	 */
	@Beta
	public abstract ScriptingAPI getScripting();
}