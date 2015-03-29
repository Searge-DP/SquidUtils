/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import net.minecraft.command.ICommandSender;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;

public class CommandInfo extends CommandBase {
	
	private final ChatMessage message;
	
	public CommandInfo(String name, String desc, String text) {
		super(name, desc);
		this.message = new ChatMessage(text);
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(this.message);
	}

	public CommandInfo setUrl(String url) {
		this.message.setUrl(url);
		return this;
	}
}