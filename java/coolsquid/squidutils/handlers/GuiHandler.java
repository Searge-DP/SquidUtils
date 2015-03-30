/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.io.File;

import net.minecraftforge.client.event.GuiOpenEvent;
import coolsquid.squidapi.config.SquidAPIConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler {

	private final SquidAPIConfig guis = new SquidAPIConfig(new File("./config/SquidUtils/GUIs.cfg"));

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null && this.guis.get(event.gui.getClass().getName(), false)) {
			event.setCanceled(true);
		}
	}
}