/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.command;

import java.util.List;

import net.minecraft.command.ICommandSender;

import com.github.coolsquid.squidapi.command.CommandBase;
import com.github.coolsquid.squidapi.helpers.server.ServerHelper;

public class CommandCustom extends CommandBase {

	public CommandCustom(String name, String desc, boolean needsop) {
		super(name, desc, needsop);
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return true;
	}
	
	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args) {
		return ServerHelper.getAllDisplayNames();
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		
	}
}