/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import java.io.File;

import com.google.gson.Gson;

import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidutils.SquidUtils;


public abstract class CustomContentHandler<E> {

	private final File dir;
	private final Class<E> type;

	public CustomContentHandler(String subdir, Class<E> type) {
		this.dir = new File("./config/SquidUtils/custom/" + subdir);
		if (!this.dir.exists()) {
			this.dir.mkdirs();
		}
		this.type = type;
	}

	public void load(Gson gson) {
		for (File file: this.dir.listFiles()) {
			try {
				SquidUtils.instance().info("Found content file: " + file.getPath());
				this.handle(gson.fromJson(IOUtils.readAll(file), this.type));
			} catch (Throwable t) {
				SquidUtils.instance().error(t);
			}
		}
	}

	protected abstract void handle(E fromJson);
}