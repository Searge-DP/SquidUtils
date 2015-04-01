/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBoxHandler {

	public static final BlockBoxHandler INSTANCE = new BlockBoxHandler();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawBox(DrawBlockHighlightEvent event) {
		event.setCanceled(true);
	}
}