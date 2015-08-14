/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config.custom;

import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.util.objects.CrashCallable;

public class CrashCallableCreationHandler extends CreationHandler<CrashCallable> {

	public static final CrashCallableCreationHandler INSTANCE = new CrashCallableCreationHandler();

	public CrashCallableCreationHandler() {
		super("crashcallables", CrashCallable[].class);
	}

	@Override
	protected void handle(CrashCallable fromJson) {
		SquidAPI.COMMON.registerCallable(fromJson);
	}
}