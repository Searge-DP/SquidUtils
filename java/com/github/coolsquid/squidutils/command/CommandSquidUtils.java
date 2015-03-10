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
import com.github.coolsquid.squidutils.SquidUtils;

public class CommandSquidUtils extends CommandBase {

	public CommandSquidUtils() {
		super("SquidUtils", "");
	}
	
	public static class Unregister implements ISubCommand {

		@Override
		public String getName() {
			return "unregister";
		}

		@Override
		public void execute(ICommandSender sender, List<String> args) {
			MinecraftForge.EVENT_BUS.unregister(SquidUtils.handlers.get(args.get(0)));
		}
	}
}