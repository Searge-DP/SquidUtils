/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.command.OpOnly;
import coolsquid.squidapi.handlers.MonetizationHandler;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;
import coolsquid.squidutils.helpers.PermissionHelper;

public class CommandPermissions extends CommandBase implements OpOnly {

	public CommandPermissions() {
		super("permissions", "");
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (MonetizationHandler.a(sender)) {
			sender.addChatMessage(new ChatMessage("<SquidAPI> The console is not allowed to execute /permissions!"));
			return;
		}
		try {
			if (args[0].equals("add")) {
				PermissionHelper.INSTANCE.addPermission(ServerHelper.getPlayerFromNick(args[1]).getGameProfile().getId(), args[2]);
			}
			else if (args[0].equals("remove")) {
				PermissionHelper.INSTANCE.removePermission(ServerHelper.getPlayerFromNick(args[1]).getGameProfile().getId(), args[2]);
			}
			else if (args[0].equals("help")) {
				sender.addChatMessage(new ChatMessage("<SquidUtils> Available subcommands:").setColor(EnumChatFormatting.RED));
				sender.addChatMessage(new ChatMessage("/permissions add username permission").setColor(EnumChatFormatting.RED));
				sender.addChatMessage(new ChatMessage("/permissions remove username permission").setColor(EnumChatFormatting.RED));
			}
		} catch (Exception e) {
			sender.addChatMessage(new ChatMessage("<SquidUtils> Wrong syntax. Please do \"/permissions help\" for more information.").setColor(EnumChatFormatting.RED));
		}
	}
	
	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args) {
		if (args.length == 2) {
			return ServerHelper.getAllDisplayNames();
		}
		return null;
	}
}