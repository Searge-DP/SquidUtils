/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.event;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Cancelable;

/**
 * Fired whenever an item is registered in {@link GameRegistry.registerItem(item, name, modId);}<br>
 *
 * <br>This event is {@link Cancelable}.
 * If this event is canceled, the item will never be registered.<br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 *
 * @author CoolSquid
 */
@Cancelable
public class ItemRegisterEvent extends RegisterEvent {

	private final Item item;

	/**
	 * Instantiates a new ItemRegisterEvent.
	 *
	 * @param item the item
	 * @param name the name
	 */
	public ItemRegisterEvent(Item item, String name) {
		super(name);
		this.item = item;
	}

	/**
	 * Instantiates a new ItemRegisterEvent.
	 *
	 * @param item the item
	 * @param name the name
	 * @param mod the mod
	 */
	public ItemRegisterEvent(Item item, String name, String mod) {
		super(name, mod);
		this.item = item;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public Item getItem() {
		return this.item;
	}
}