/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GameOverlayHandler {

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent event) {
		if (SquidUtils.COMMON.getDisabledOverlays().contains(event.type)) {
			event.setCanceled(true);
		}
	}
}