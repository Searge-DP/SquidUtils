package com.github.coolsquid.SquidCore;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.SquidCore.CreativeTabs.ColorTab;
import com.github.coolsquid.SquidCore.CreativeTabs.SpawnEggTab;
import com.github.coolsquid.SquidCore.CreativeTabs.VanillaTab;
import com.github.coolsquid.SquidCore.CreativeTabs.WoodTab;
import com.github.coolsquid.SquidCore.LogHandler.LogHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

@Mod(modid = "squidcore", name = "SquidCore", version = "1.0.0")
public class SquidCore {
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		LogHandler.info(Ref.M, "Initializing creative tabs");
		
		WoodTab.PreInit(event);
		ColorTab.PreInit(event);
		SpawnEggTab.PreInit(event);
		VanillaTab.PreInit(event);
		
		MinecraftForge.EVENT_BUS.register((Object)new MFRHandler());
	}
}
