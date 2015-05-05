/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.util;

import java.util.Map;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.Maps;

import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;

public class EventHandlerManager implements IEventHandlerRegistrationManager {

	private final Map<String, EventHandler> eventHandlers = Maps.newHashMap();

	public Map<String, EventHandler> getEventHandlers() {
		return this.eventHandlers;
	}

	/* (non-Javadoc)
	 * @see coolsquid.squidutils.util.IEventHandlerRegistrationManager#register(java.lang.Object, cpw.mods.fml.common.eventhandler.EventBus)
	 */
	@Override
	public void register(Object handler, EventBus bus) {
		try {
			String name = handler.getClass().getSimpleName();
			SquidUtils.instance().info("Registering handler ", name, ".");
			bus.register(handler);
			this.eventHandlers.put(name, new EventHandler(bus, handler));
		} catch (Throwable t) {
			SquidUtils.instance().error(t);
		}
	}

	public void registerFMLHandler(Object handler) {
		this.register(handler, FMLCommonHandler.instance().bus());
	}

	public void registerForgeHandler(Object handler) {
		this.register(handler, MinecraftForge.EVENT_BUS);
	}

	public void unregister(String name) {
		try {
			EventHandler handler = this.getEventHandlers().get(name);
			handler.getBus().unregister(handler.getHandler());
		} catch (Throwable t) {
			SquidUtils.instance().error(t);
		}
	}

	public void registerAll() {
		for (EventHandler handler: this.eventHandlers.values()) {
			this.register(handler.getHandler(), handler.getBus());
		}
	}

	public void unregisterAll() {
		for (String name: this.eventHandlers.keySet()) {
			this.unregister(name);
		}
	}

	public static EventHandlerManager create() {
		return new EventHandlerManager();
	}
}