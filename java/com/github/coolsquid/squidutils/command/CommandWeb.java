/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.command;

import net.minecraft.command.ICommandSender;

import com.github.coolsquid.squidapi.command.CommandBase;
import com.github.coolsquid.squidutils.handlers.command.WebHandler;

public class CommandWeb extends CommandBase {
	
	private String url;
	
	public CommandWeb(String name, String desc, String url) {
		super(name, desc);
		this.url = url;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		new WebHandler(sender, this.url).start();
	}
}