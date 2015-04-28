/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.helpers;

import java.util.Map;
import java.util.Set;

import coolsquid.squidapi.reflection.ReflectionHelper;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenHelper {

	public static final Set<IWorldGenerator> worldGenerators = ReflectionHelper.in(GameRegistry.class).field("worldGenerators", "worldGenerators").get();
	public static final Map<IWorldGenerator, Integer> worldGeneratorIndex = ReflectionHelper.in(GameRegistry.class).field("worldGeneratorIndex", "worldGeneratorIndex").get();

	public static void remove(IWorldGenerator w) {
		worldGenerators.remove(w);
		worldGeneratorIndex.remove(w);
	}
}