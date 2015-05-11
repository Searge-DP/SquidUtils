/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.living.LivingHealEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HealingHandler implements IEventTrigger {
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}
	
	@SubscribeEvent
	public void onHeal(LivingHealEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("maxamount") || !a.values.containsKey("minamount") || (event.amount < Float.parseFloat((String) a.values.get("maxamount")) && event.amount > Float.parseFloat((String) a.values.get("minamount")))) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}