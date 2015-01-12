package com.github.coolsquid.SquidUtils.Utils;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Handlers.Tweakers.RecipeHandler;
import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class EnvironmentChecks {
	
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