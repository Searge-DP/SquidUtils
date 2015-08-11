/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import coolsquid.squidutils.SquidUtils;


public abstract class CreationHandler<E> {

	private final File dir;
	private final Class<E[]> type;

	public CreationHandler(String subdir, Class<E[]> type) {
		this.dir = new File("./config/SquidUtils/custom/" + subdir);
		if (!this.dir.exists()) {
			this.dir.mkdirs();
		}
		this.type = type;
	}

	final void load(Gson gson) {
		for (File file: this.dir.listFiles()) {
			try {
				SquidUtils.log.info("Found content file: " + file.getPath());
				for (E e: gson.fromJson(FileUtils.readFileToString(file), this.type)) {
					this.handle(e);
				}
			} catch (Throwable t) {
				SquidUtils.log.catching(t);
			}
		}
	}

	protected abstract void handle(E fromJson);
}