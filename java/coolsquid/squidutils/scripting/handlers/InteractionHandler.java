/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
public class InteractionHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
			if (event.entityPlayer.getHeldItem() != null) {
				Item item = event.entityPlayer.getHeldItem().getItem();
				for (EventInfo a: info) {
					if (!a.values.containsKey("item") || a.values.get("item") == item) {
						EventEffectHelper.performEffects(a, event.entityLiving);
					}
				}
			}
		}
	}
}