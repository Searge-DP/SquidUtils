package com.github.coolsquid.SquidUtils;

import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.SquidUtils.CreativeTabs.*;
import com.github.coolsquid.SquidUtils.Handlers.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

@Mod(modid = "squidutils", name = "SquidUtils", version = "1.0.0")
public class SquidUtils {
	
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
