package com.github.coolsquid.SquidCore;

import net.minecraftforge.oredict.OreDictionary;

import com.github.coolsquid.SquidCore.Handlers.LogHandler;
//import com.github.coolsquid.SquidCore.Handlers.RecipeHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
