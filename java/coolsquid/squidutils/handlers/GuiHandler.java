/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.GuiOpenEvent;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null) {
			if (event.gui instanceof GuiChat && ModConfigHandler.INSTANCE.defaultChatText != null) {
				GuiChat chat = (GuiChat) event.gui;
				chat.defaultInputFieldText = ModConfigHandler.INSTANCE.defaultChatText;
			}
		}
	}

	/*@SubscribeEvent
	public void onInitGui(InitGuiEvent event) {
		String guiname = event.gui.getClass().getSimpleName();
		if (!this.configs.containsKey(guiname)) {
			this.configs.put(guiname, new Configuration(new SquidAPIFile("./config/SquidUtils/GUIs/" + guiname + ".cfg")));
		}
		Configuration config = this.configs.get(guiname);
		for (int a = 0; a < event.buttonList.size(); a++) {
			GuiButton button = (GuiButton) event.buttonList.get(a);
			String name = "button:" + button.id;
			String text = config.get(name, "displayString", "").getString();
			if (!text.isEmpty()) {
				button.displayString = text;
			}
			button.enabled = config.get(name, "enabled", button.enabled).getBoolean();
			button.visible = config.get(name, "visible", button.visible).getBoolean();
			button.height = config.get(name, "height", button.height).getInt();
			button.width = config.get(name, "width", button.width).getInt();
			button.xPosition = config.get(name, "xPosition", button.xPosition).getInt();
			button.yPosition = config.get(name, "yPosition", button.yPosition).getInt();
			button.packedFGColour = config.get(name, "color", button.packedFGColour).getInt();
		}
		if (config.hasChanged()) {
			config.save();
		}
	}*/
}