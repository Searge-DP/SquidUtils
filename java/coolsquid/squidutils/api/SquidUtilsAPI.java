/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;
import coolsquid.squidapi.util.collect.Registry;

/**
 * Instructions may be found at {@link http://coolsquidmc.blogspot.no/2015/05/using-squidutils-api.html}
 * @author CoolSquid
 */

public interface SquidUtilsAPI {

	/**
	 * Registers a damage source.
	 * @param source the damagesource to register
	 */
	public abstract void registerDamageSource(DamageSource source);

	/**
	 * Registers a material.
	 * @param name the name of the material
	 * @param material the material to register
	 */
	public abstract void registerMaterial(String name, Material material);

	/**
	 * Gets the damage sources.
	 * @return the damage sources
	 */
	public abstract Registry<DamageSource> getDamageSources();

	/**
	 * Gets the materials.
	 * @return the materials
	 */
	public abstract Registry<Material> getMaterials();

	/**
	 * @return the scripting API if SquidUtils|Scripting is loaded, or null if it's not.
	 */
	public abstract ScriptingAPI getScripting();
}