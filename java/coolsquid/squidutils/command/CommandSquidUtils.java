/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.command.ISubCommand;
import coolsquid.squidapi.command.OpOnly;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;
import coolsquid.squidapi.helpers.server.chat.ChatMessage.Color;
import coolsquid.squidutils.SquidUtils;

public class CommandSquidUtils extends CommandBase implements OpOnly {

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
				SquidUtils.API.getEventHandlerManager().unregister(args.get(0));
				sender.addChatMessage(new ChatMessage("<SquidUtils> Handler successfully unregistered!"));
			} catch (Exception e) {
				sender.addChatMessage(new ChatMessage("<SquidUtils> ", e.getClass().getName()).setColor(Color.RED));
				e.printStackTrace();
			}
		}
	}
}