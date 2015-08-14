/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.command.ICommandSender;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;

public class CommandOpenUrl extends CommandBase {

	private final URI uri;

	public CommandOpenUrl(String name, String desc, String url) {
		super(name, desc);
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			uri = null;
			SquidAPI.log.catching(e);
		}
		this.uri = uri;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!Desktop.isDesktopSupported()) {
			sender.addChatMessage(new ChatMessage("<SquidAPI> Your system is not supported."));
		}
		else {
			if (this.uri != null) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				sender.addChatMessage(new ChatMessage("<SquidAPI> Something failed D:"));
			}
		}
	}
}