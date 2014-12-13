package com.github.coolsquid.SquidUtils.Handlers;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExceptionHandler {

	private static final ArrayList<String> DIR_LIST = new ArrayList<String>();
	
	public static void arrayInit() {
		DIR_LIST.add(".technic");
		DIR_LIST.add(".Technic");
		DIR_LIST.add("Technic");
		DIR_LIST.add("technic");
		DIR_LIST.add("forge");
	}
	
	public static String get(int i) {
		return DIR_LIST.get(i);
	}
	
	public static int size() {
		return DIR_LIST.size();
	}
	
	@SubscribeEvent
	public void Update(LivingUpdateEvent event) {
		Minecraft.getMinecraft().gameSettings.limitFramerate = 10;
	}
}