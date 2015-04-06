/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import java.awt.Desktop;
import java.net.URI;

import net.minecraft.command.ICommandSender;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;
import coolsquid.squidapi.util.io.WebUtils;

public class CommandOpenUrl extends CommandBase {

	private final URI uri;

	public CommandOpenUrl(String name, String desc, String url) {
		super(name, desc);
		this.uri = WebUtils.newURI(url);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!Desktop.isDesktopSupported()) {
			sender.addChatMessage(new ChatMessage("<SquidAPI> Your system is not supported."));
		}
		else {
			WebUtils.openBrowser(this.uri);
		}
	}
}