package com.github.coolsquid.SquidUtils.Handlers;

import org.apache.logging.log4j.Level;

import net.minecraftforge.oredict.OreDictionary;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;
import com.github.coolsquid.SquidUtils.Utils.LogHelper;

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
				LogHelper.warn("Skyggy_sky_do_please_kindly_shut_up");
				a++;
			}
		}
		if (event.Name.length() > 20 && ConfigHandler.getOreDictComplain()) {
			LogHelper.bigWarning(Level.WARN, "Oredictionary entry \"" + event.Name + "\" is very long. Please do not use more than 20 symbols.");
		}
	}
}