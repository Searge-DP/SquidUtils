package com.github.coolsquid.squidutils.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;

import cpw.mods.fml.common.Loader;

public class ModLister {
	
	public static final void init() {
		if (ConfigHandler.generateModList == 0) {
			return;
		}
		else if (ConfigHandler.generateModList == 1) {
			generateListOfModids();
		}
		else if (ConfigHandler.generateModList == 2) {
			generateListOfModidsAndVersions();
		}
	}
	
	public static final void generateListOfModids() {
		File f = new File("modlist.txt");
		LogHelper.info("Generating modlist...");
		try {
			f.createNewFile();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			for (int a = 0; a < Loader.instance().getModList().size(); a++) {
				w.write(Loader.instance().getModList().get(a).getModId());
				w.write("\n");
			}
			w.close();
			LogHelper.info("Generated modlist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final void generateListOfModidsAndVersions() {
		File f = new File("modlist.txt");
		LogHelper.info("Generating modlist...");
		try {
			f.createNewFile();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			for (int a = 0; a < Loader.instance().getModList().size(); a++) {
				w.write(Loader.instance().getModList().get(a).getModId());
				w.write(" ");
				w.write(Loader.instance().getModList().get(a).getVersion());
				w.write("\n");
			}
			w.close();
			LogHelper.info("Generated modlist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}