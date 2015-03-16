/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;

import com.github.coolsquid.squidapi.command.CommandBase;
import com.github.coolsquid.squidapi.command.ISubCommand;
import com.github.coolsquid.squidapi.helpers.server.chat.ChatMessage;
import com.github.coolsquid.squidapi.helpers.server.chat.ChatMessage.Color;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.SquidUtils;

public class CommandSquidUtils extends CommandBase {

	public CommandSquidUtils() {
		super("SquidUtils", "", new SubcommandUnregister());
	}
	
	private static class SubcommandUnregister implements ISubCommand {

		@Override
		public String getName() {
			return "unregister";
		}

		@Override
		public void execute(ICommandSender sender, List<String> args) {
			if (args.size() < 1) {
				sender.addChatMessage(new ChatMessage("Not enough arguments."));
				return;
			}
			try {
				MinecraftForge.EVENT_BUS.unregister(SquidUtils.instance().handlers.get(args.get(0)));
				sender.addChatMessage(new ChatMessage("<SquidUtils> Handler successfully unregistered!"));
			} catch (Exception e) {
				sender.addChatMessage(new ChatMessage("<SquidUtils> ", e.getClass().getName()).setColor(Color.RED));
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return Utils.isClient();
	}
}