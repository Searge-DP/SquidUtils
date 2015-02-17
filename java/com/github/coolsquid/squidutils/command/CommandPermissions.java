package com.github.coolsquid.squidutils.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

import com.github.coolsquid.squidapi.chat.ChatMessage;
import com.github.coolsquid.squidapi.command.CommandBase;
import com.github.coolsquid.squidapi.helpers.server.ServerHelper;
import com.github.coolsquid.squidutils.helpers.PermissionHelper;

public class CommandPermissions extends CommandBase {

	public CommandPermissions() {
		super("permissions", "", true);
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		try {
			if (args[0].equals("add")) {
				PermissionHelper.addPermission(ServerHelper.getPlayerFromNick(args[1]).getGameProfile().getId(), args[2]);
			}
			else if (args[0].equals("remove")) {
				PermissionHelper.removePermission(ServerHelper.getPlayerFromNick(args[1]).getGameProfile().getId(), args[2]);
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