package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
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
			while (a <= 5) {
				LogHandler.warn("Skyggy_sky_do_please_kindly_shut_up");
				a++;
			}
			//RecipeHandler.OreDictRecipeBlock("Greggy_greg_do_please_kindly_stuff_a_sock_in_it_xxx", Blocks.diamond_block);
		}
		else if (event.Name.length() > 25) {
			LogHandler.bigWarning("Too long oredictionary entry! This is not allowed due to SquidCore.");
			FMLCommonHandler.instance().exitJava(1, false);
		}
	}
}
