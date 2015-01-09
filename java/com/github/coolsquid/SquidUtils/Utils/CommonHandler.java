package com.github.coolsquid.SquidUtils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class CommonHandler {
	
	public static final void init() {
		File file = new File("./crash-reports/README-I-AM-VERY-IMPORTANT.txt");
		try {
			if (file.exists()) {
				file.createNewFile();
				PrintWriter w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
				w.format("Read through crash reports before posting them!\n");
				w.format("If you do not do this, you might be ignored, or in worst case banned for spamming.\n");
				w.close();
			}
			else if (file.exists())
				file.setLastModified(0);
		} catch (IOException e) {
			
		}
		
		if (Data.isClient())
			EnvironmentChecks.preInit();
		
		if (Data.isBukkit())
			LogHelper.warn("Running on Bukkit! No support will be given.");
	}
}