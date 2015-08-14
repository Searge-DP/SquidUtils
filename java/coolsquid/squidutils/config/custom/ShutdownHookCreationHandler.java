/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import org.apache.logging.log4j.message.SimpleMessage;

import coolsquid.squidapi.SquidAPI;


public class ShutdownHookCreationHandler extends CreationHandler<SimpleMessage> {

	public static final ShutdownHookCreationHandler INSTANCE = new ShutdownHookCreationHandler();

	public ShutdownHookCreationHandler() {
		super("shutdownmessages", SimpleMessage[].class);
	}

	@Override
	protected void handle(SimpleMessage fromJson) {
		SquidAPI.COMMON.registerShutdownMessage(fromJson);
	}
}