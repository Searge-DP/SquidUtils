/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting;

import net.minecraftforge.common.MinecraftForge;
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
import cpw.mods.fml.common.FMLCommonHandler;

public class SquidUtilsScripting {

	public static void init() {
		SquidUtils.log.info("Initializing.");

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

		if (ScriptHandler.INSTANCE.onCraft) {
			FMLCommonHandler.instance().bus().register(new CraftingHandler());
		}
		if (ScriptHandler.INSTANCE.onSmelt) {
			FMLCommonHandler.instance().bus().register(new SmeltingHandler());
		}
		if (ScriptHandler.INSTANCE.onHurt) {
			MinecraftForge.EVENT_BUS.register(new DamageHandler());
		}
		if (ScriptHandler.INSTANCE.onHeal) {
			MinecraftForge.EVENT_BUS.register(new HealingHandler());
		}
		if (ScriptHandler.INSTANCE.onToss) {
			MinecraftForge.EVENT_BUS.register(new TossHandler());
		}
		if (ScriptHandler.INSTANCE.onExplosion) {
			MinecraftForge.EVENT_BUS.register(new ExplosionHandler());
		}
		if (ScriptHandler.INSTANCE.onInteraction) {
			MinecraftForge.EVENT_BUS.register(new InteractionHandler());
		}

		SquidUtils.log.info("Finished initialization.");
	}
}