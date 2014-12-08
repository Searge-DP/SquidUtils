package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */
 
public class ModHandler {
	
	@SubscribeEvent
	public void oreDictHandler(OreDictionary.OreRegisterEvent event) {
		if (event.Name == "Greggy_greg_do_please_kindly_stuff_a_sock_in_it") {
			int a = 1;
			while (a <= ConfigHandler.MFR) {
				LogHandler.warn("Skyggy_sky_do_please_kindly_shut_up");
				a++;
			}
		}
		if (event.Name.length() > 20 && ConfigHandler.OreDictComplain) {
			LogHandler.warn("Oredictionary entry \"" + event.Name + "\" is very long. Please do not use more than 20 symbols.");
		}
	}
	
	/**
	public static void OreDictRecipeBlock() {
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.diamond_block, true, new Object[]{
				"G", Character.valueOf('G'), "Greggy_greg_do_please_kindly_stuff_a_sock_in_it_xxx"}));
	}
	*/
}