/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolTipHandler {

	public static final ToolTipHandler INSTANCE = new ToolTipHandler();

	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		if (event.itemStack != null && SquidUtils.API.getTooltips().containsKey(event.itemStack.getItem())) {
			for (String tooltip: SquidUtils.API.getTooltips().get(event.itemStack.getItem())) {
				event.toolTip.add(tooltip);
			}
		}
	}
}