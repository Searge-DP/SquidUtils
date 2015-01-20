package com.github.coolsquid.squidutils.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.github.coolsquid.squidutils.util.CrashReportInterceptor;
import com.github.coolsquid.squidutils.util.Data;
import com.github.coolsquid.squidutils.util.EnvironmentChecks;

import cpw.mods.fml.common.FMLCommonHandler;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class CommonHelper {
	
	/**
	 * Generates a crash reporting info file and starts the environment checks.
	 */
	
	public static final void init() {
		File file = new File("./crash-reports/README-I-AM-VERY-IMPORTANT.txt");
		try {
			if (file.exists()) {
				file.createNewFile();
				PrintWriter w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
				w.format("Read through crash reports before posting them!\n");
				w.format("If you do not do this, you might be ignored, or in worst case banned for spamming.\n");
				w.format("Learn how to write bug reports at: http://vazkii.us/br101/");
				w.close();
			}
			else if (file.exists())
				file.setLastModified(0);
		} catch (IOException e) {
			
		}
		
		FMLCommonHandler.instance().registerCrashCallable(new CrashReportInterceptor());
		
		if (Data.isClient())
			EnvironmentChecks.preInit();
		
		if (Data.isBukkit())
			LogHelper.warn("Running on Bukkit! No support will be given.");
	}
}