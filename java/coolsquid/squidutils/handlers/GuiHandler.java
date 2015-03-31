/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.io.File;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import coolsquid.squidapi.config.SquidAPIConfig;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler {

	private final SquidAPIConfig guis = new SquidAPIConfig(new File("./config/SquidUtils/GUIs.cfg"));
	private final SquidAPIConfig buttons = new SquidAPIConfig(new File("./config/SquidUtils/Buttons.cfg"));

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null && this.guis.get(event.gui.getClass().getSimpleName(), false)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onInitGui(InitGuiEvent event) {
		for (int a = 0; a < event.buttonList.size(); a++) {
			GuiButton button = (GuiButton) event.buttonList.get(a);
			if (this.buttons.get(event.gui.getClass().getSimpleName() + ":" + button.displayString.replace(" ", "_"), false)) {
				event.buttonList.remove(a);
			}
		}
	}
}