/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.github.coolsquid.squidapi.command.CommandBase;

public class CommandInfo extends CommandBase {
	
	private String text;
	
	public CommandInfo(String name, String desc, String text) {
		super(name, desc);
		this.text = text;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(new ChatComponentText(text));
	}
}