package com.github.coolsquid.SquidUtils;

import net.minecraftforge.oredict.OreDictionary;

import com.github.coolsquid.SquidUtils.Handlers.LogHandler;
//import com.github.coolsquid.SquidUtils.Handlers.RecipeHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */
 
public class MFRHandler {
	
	@SubscribeEvent
	public void oredict(OreDictionary.OreRegisterEvent event) {
		if (event.Name == "Greggy_greg_do_please_kindly_stuff_a_sock_in_it_xxx") {
			int a = 1;
			while (a <= 20) {
				LogHandler.warn("Skyggy_sky_do_please_kindly_stuff_a_sock_in_it_xxx");
				a++;
			}
			RecipeHandler.OreDictRecipe("Greggy_greg_do_please_kindly_stuff_a_sock_in_it_xxx");
		}
	}
}
