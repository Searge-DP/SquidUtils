/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolTipHandler {

	public static final ToolTipHandler INSTANCE = new ToolTipHandler();

	private final Map<Item, List<String>> tooltips = Maps.newHashMap();

	public Map<Item, List<String>> getTooltips() {
		return this.tooltips;
	}

	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		if (event.itemStack != null && this.tooltips.containsKey(event.itemStack.getItem())) {
			for (String tooltip: this.tooltips.get(event.itemStack.getItem())) {
				event.toolTip.add(tooltip);
			}
		}
	}
}