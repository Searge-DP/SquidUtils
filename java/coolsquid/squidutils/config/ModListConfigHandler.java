/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import coolsquid.squidapi.config.impl.ConfigHandlerImpl;
import coolsquid.squidutils.util.PackIntegrityChecker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModListConfigHandler extends ConfigHandlerImpl {

	public static final ModListConfigHandler INSTANCE = new ModListConfigHandler(new File("./config/SquidUtils/Modlist.cfg"));

	public String warningScreenLine1 = "Some of the mods installed might not work with this modpack.";
	public String warningScreenLine2 = "Please do not report any issues.";
	public String warningScreenLine3 = "The following mods are unsupported:";
	public int generateModList = 0;

	public ModListConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		String[] a = this.config.getStringList("unsupportedMods", "modLists", new String[] {}, "List of unsupported mods.");
		String[] b = this.config.getStringList("mods", "modLists", new String[] {}, "List of supported mods.");

		this.warningScreenLine1 = this.config.getString("warningScreenLine1", "localization", "Some of the mods installed might not work with this modpack.", "");
		this.warningScreenLine2 = this.config.getString("warningScreenLine2", "localization", "Please do not report any issues.", "");
		this.warningScreenLine3 = this.config.getString("warningScreenLine3", "localization", "The following mods are unsupported:", "");

		this.generateModList = this.config.getInt("generateModList", "general", 0, 0, 2, "Generates a list of modids in the working directory. Set to 1 to generate only modids, or set to 2 to generate modids and versions.");

		PackIntegrityChecker.INSTANCE.check(a, b);
	}
}