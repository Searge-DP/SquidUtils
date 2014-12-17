package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraftforge.oredict.OreDictionary;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

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
			while (a <= ConfigHandler.getMFR()) {
				LogHandler.warn("Skyggy_sky_do_please_kindly_shut_up");
				a++;
			}
		}
		if (event.Name.length() > 20 && ConfigHandler.getOreDictComplain()) {
			LogHandler.bigWarning("Oredictionary entry \"" + event.Name + "\" is very long. Please do not use more than 20 symbols.");
		}
	}
}