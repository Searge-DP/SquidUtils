/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraft.command.ICommand;
import net.minecraftforge.common.config.ConfigCategory;
import coolsquid.squidapi.command.CommandBase;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.util.io.SquidAPIFile;
import coolsquid.squidutils.command.CommandInfo;
import coolsquid.squidutils.command.CommandOpenUrl;
import coolsquid.squidutils.command.CommandWeb;

public class CommandConfigHandler extends ConfigHandler {

	public static final CommandConfigHandler INSTANCE = new CommandConfigHandler(new SquidAPIFile("./config/SquidUtils/Commands.cfg"));

	private CommandConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ConfigCategory category: this.config.getCategory("customCommands").getChildren()) {
			ICommand command = null;
			String type = category.get("type").getString();
			String name = category.getName();
			String desc = category.get("description").getString();
			if (type.equals("info")) {
				command = new CommandInfo(name, desc, category.get("text").getString());
			}
			else if (type.equals("base")) {
				command = new CommandBase(name, desc);
			}
			else if (type.equals("infoFromWeb")) {
				command = new CommandWeb(name, desc, category.get("url").getString());
			}
			else if (type.equals("openUrl")) {
				command = new CommandOpenUrl(name, desc, category.get("url").getString());
			}
			ServerHelper.registerCommand(command);
		}
		String[] names = ServerHelper.getCommands().keySet().toArray(new String[] {});
		for (int a = 0; a < ServerHelper.getCommands().size(); a++) {
			String name = names[a];
			if (!this.config.get(name, "enabled", true).getBoolean()) {
				ServerHelper.removeCommand(name);
			}
		}
	}
}