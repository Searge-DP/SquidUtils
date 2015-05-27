/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.event;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Cancelable;

/**
 * Fired whenever a block is registered in {@link GameRegistry.registerBlock(block, itemclass, name, itemCtorArgs);}<br>
 *
 * <br>This event is {@link Cancelable}.
 * If this event is canceled, the item will never be registered.<br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 *
 * @author CoolSquid
 */
@Cancelable
public class BlockRegisterEvent extends RegisterEvent {

	private final Block block;

	/**
	 * Instantiates a new BlockRegisterEvent.
	 *
	 * @param block the block
	 * @param name the name
	 */
	public BlockRegisterEvent(Block block, String name) {
		super(name);
		this.block = block;
	}

	/**
	 * Instantiates a new BlockRegisterEvent.
	 *
	 * @param block the block
	 * @param name the name
	 * @param mod the mod
	 */
	public BlockRegisterEvent(Block block, String name, String mod) {
		super(name, mod);
		this.block = block;
	}

	/**
	 * Gets the block.
	 *
	 * @return the block
	 */
	public Block getBlock() {
		return this.block;
	}
}