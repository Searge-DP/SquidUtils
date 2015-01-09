package com.github.coolsquid.SquidUtils.Handlers;

import net.minecraftforge.common.MinecraftForge;

public class RunEventLogger implements Runnable {
	
	@Override
	public void run() {
		MinecraftForge.EVENT_BUS.register((Object)new EventLogger());
	}
}