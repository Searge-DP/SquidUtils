/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class SimpleConfig {

	private final Properties props;
	private final File file;

	SimpleConfig(File file) throws IOException {
		this.props = new Properties();
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			this.props.load(in);
			in.close();
		}
		this.file = file;
	}

	public String getString(String key, String defaultValue) {
		if (this.props.containsKey(key)) {
			defaultValue = this.props.getProperty(key);
		}
		else {
			this.props.setProperty(key, defaultValue);
		}
		return defaultValue;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		if (this.props.containsKey(key)) {
			defaultValue = Boolean.parseBoolean(this.props.getProperty(key));
		}
		else {
			this.props.setProperty(key, String.valueOf(defaultValue));
		}
		return defaultValue;
	}

	public int getInt(String key, int defaultValue) {
		if (this.props.containsKey(key)) {
			defaultValue = Integer.parseInt(this.props.getProperty(key));
		}
		else {
			this.props.setProperty(key, String.valueOf(defaultValue));
		}
		return defaultValue;
	}

	public double getDouble(String key, double defaultValue) {
		if (this.props.containsKey(key)) {
			defaultValue = Double.parseDouble(this.props.getProperty(key));
		}
		else {
			this.props.setProperty(key, String.valueOf(defaultValue));
		}
		return defaultValue;
	}

	public void save() throws IOException {
		this.save(null);
	}

	public void save(String comment) throws IOException {
		FileOutputStream out = new FileOutputStream(this.file);
		this.props.store(out, comment);
		out.close();
	}
}