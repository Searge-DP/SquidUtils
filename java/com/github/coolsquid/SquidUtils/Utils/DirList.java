package com.github.coolsquid.SquidUtils.Utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.github.coolsquid.SquidUtils.Utils.Logging.LogHelper;

//Will be used for known modpacks illegally featuring SquidUtils
public class DirList {
	
	private static List<String> DIR_LIST = new ArrayList<String>();
	
	public static final void stop() {
		LogHelper.bigWarning(Level.WARN, "You are running an illegal modpack! Please report this at: " + Data.forum);
	}
	
	public static final void add(String dir) {
		DIR_LIST.add(dir);
	}
}