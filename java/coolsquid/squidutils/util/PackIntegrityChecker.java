/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.io.File;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidutils.config.GeneralConfigHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


public class PackIntegrityChecker {

	public static final PackIntegrityChecker INSTANCE = new PackIntegrityChecker();

	private final Set<String> set = Sets.newHashSet();
	private final Set<String> alreadyWarned;
	private final File data = new File("./SquidAPI/SquidUtils/PackChecker.data");

	private PackIntegrityChecker() {
		this.alreadyWarned = IOUtils.readLines(this.data);
	}

	public void check(String[] unsupportedMods) {
		for (String mod: unsupportedMods) {
			if (Loader.isModLoaded(mod) && !this.alreadyWarned.contains(mod)) {
				this.set.add(mod);
			}
		}
		if (!this.set.isEmpty()) {
			MinecraftForge.EVENT_BUS.register(this);
			IOUtils.writeLines(this.data, this.set);
		}
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui instanceof GuiMainMenu) {
			event.gui = new IncompatibleModsWarning();
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}

	private class IncompatibleModsWarning extends GuiScreen {

		@Override
		public void drawScreen(int mouseRelX, int mouseRelY, float tickTime) {
			this.drawDefaultBackground();
			super.drawScreen(mouseRelX, mouseRelY, tickTime);
			String string = GeneralConfigHandler.INSTANCE.warningScreenLine1;
			String string2 = GeneralConfigHandler.INSTANCE.warningScreenLine2;
			String string3 = GeneralConfigHandler.INSTANCE.warningScreenLine3;
			String string4 = Joiner.on(", ").join(PackIntegrityChecker.this.set);
			this.drawCenteredString(this.fontRendererObj, string, this.width / 2, this.height / 2 - 30, 16777215);
			this.drawCenteredString(this.fontRendererObj, string2, this.width / 2, this.height / 2 - 20, 16777215);
			this.drawCenteredString(this.fontRendererObj, string3, this.width / 2, this.height / 2, 16777215);
			this.drawCenteredString(this.fontRendererObj, string4, this.width / 2, this.height / 2 + 13, 16777215);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void initGui() {
			GuiButton button = new GuiButton(0, 0, this.height / 2 + 60, "Continue");
			button.xPosition = this.width / 2 - (button.width / 2);
			this.buttonList.add(button);
		}

		@Override
		protected void actionPerformed(GuiButton button) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
}