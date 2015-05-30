/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting;

import coolsquid.squidapi.util.EventHandlerManager;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.api.ScriptingAPI;
import coolsquid.squidutils.api.impl.ScriptingAPIImpl;
import coolsquid.squidutils.api.impl.SquidUtilsAPIImpl;
import coolsquid.squidutils.scripting.handlers.AchievementHandler;
import coolsquid.squidutils.scripting.handlers.CommandHandler;
import coolsquid.squidutils.scripting.handlers.CraftingHandler;
import coolsquid.squidutils.scripting.handlers.DamageHandler;
import coolsquid.squidutils.scripting.handlers.EntityHandler;
import coolsquid.squidutils.scripting.handlers.ExplosionHandler;
import coolsquid.squidutils.scripting.handlers.HealingHandler;
import coolsquid.squidutils.scripting.handlers.InteractionHandler;
import coolsquid.squidutils.scripting.handlers.ServerChatHandler;
import coolsquid.squidutils.scripting.handlers.SmeltingHandler;
import coolsquid.squidutils.scripting.handlers.TeleportationHandler;
import coolsquid.squidutils.scripting.handlers.TossHandler;

public class SquidUtilsScripting {

	public static void init() {
		SquidUtils.INSTANCE.info("Initializing.");

		ScriptingAPI scripting = new ScriptingAPIImpl();
		scripting.addTrigger("achievement", new AchievementHandler());
		scripting.addTrigger("command", new CommandHandler());
		scripting.addTrigger("teleport", new TeleportationHandler());
		scripting.addTrigger("craft", new CraftingHandler());
		scripting.addTrigger("smelt", new SmeltingHandler());
		scripting.addTrigger("hurt", new DamageHandler());
		scripting.addTrigger("heal", new HealingHandler());
		scripting.addTrigger("toss", new TossHandler());
		scripting.addTrigger("entityjoin", new EntityHandler());
		scripting.addTrigger("explosion", new ExplosionHandler());
		scripting.addTrigger("interact", new InteractionHandler());
		scripting.addTrigger("chat", new ServerChatHandler());
		((SquidUtilsAPIImpl) SquidUtils.API).setScripting(scripting);

		Components.init();

		ScriptHandler.INSTANCE.init();

		EventHandlerManager handlers = SquidUtils.COMMON.getEventHandlerManager();
		if (ScriptHandler.INSTANCE.onCraft) {
			handlers.registerFMLHandler(new CraftingHandler());
		}
		if (ScriptHandler.INSTANCE.onSmelt) {
			handlers.registerFMLHandler(new SmeltingHandler());
		}
		if (ScriptHandler.INSTANCE.onHurt) {
			handlers.registerForgeHandler(new DamageHandler());
		}
		if (ScriptHandler.INSTANCE.onHeal) {
			handlers.registerForgeHandler(new HealingHandler());
		}
		if (ScriptHandler.INSTANCE.onToss) {
			handlers.registerForgeHandler(new TossHandler());
		}
		if (ScriptHandler.INSTANCE.onExplosion) {
			handlers.registerForgeHandler(new ExplosionHandler());
		}
		if (ScriptHandler.INSTANCE.onInteraction) {
			handlers.registerForgeHandler(new InteractionHandler());
		}

		SquidUtils.INSTANCE.info("Finished initialization.");
	}
}