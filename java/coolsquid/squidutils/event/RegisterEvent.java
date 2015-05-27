/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.event;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event;

public abstract class RegisterEvent extends Event {

	private final String name;
	private final String mod;

	/**
	 * Instantiates a new RegisterEvent.
	 *
	 * @param name the name
	 */
	public RegisterEvent(String name) {
		this.name = name;
		this.mod = Loader.instance().activeModContainer().getModId();
	}

	/**
	 * Instantiates a new RegisterEvent.
	 *
	 * @param name the name
	 * @param mod the mod
	 */
	public RegisterEvent(String name, String mod) {
		this.name = name;
		this.mod = mod;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the mod.
	 *
	 * @return the mod
	 */
	public String getMod() {
		return this.mod;
	}
}