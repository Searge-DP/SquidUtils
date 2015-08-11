/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DebugHandler {

	@SubscribeEvent
	public final void event(RenderGameOverlayEvent.Pre event) {
		if (event.type == ElementType.DEBUG) {
			event.setCanceled(true);
			Minecraft.getMinecraft().gameSettings.showDebugInfo = false;
			Minecraft.getMinecraft().gameSettings.saveOptions();
		}
	}
}