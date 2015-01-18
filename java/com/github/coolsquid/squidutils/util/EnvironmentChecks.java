package com.github.coolsquid.squidutils.util;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.handlers.RecipeHandler;
import com.github.coolsquid.squidutils.util.logging.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class EnvironmentChecks {
	
	/**
	 * Checks if the MC version is correct, and loads dev features in decompiled environments.
	 */
	
	public static final void preInit() {
		if (Data.wrongVersion())
			LogHelper.bigWarning(Level.WARN, "MC is not running 1.7.10! Problems may occur. Do not report any errors.");
		
		if (Data.developmentEnvironment) {
			LogHelper.info("Running in a dev environment.");
			RecipeHandler.removeRecipes();
			ConfigHandler.debug = true;
		}
	}
}