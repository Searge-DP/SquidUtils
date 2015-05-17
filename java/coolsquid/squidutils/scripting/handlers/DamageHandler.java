/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DamageHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		for (EventInfo a: info) {
			if (!a.values.containsKey("maxamount") || !a.values.containsKey("minamount") || (event.ammount < (float) a.values.get("maxamount") && event.ammount > (float) a.values.get("minamount"))) {
				EventEffectHelper.performEffects(a, event.entityLiving);
			}
		}
	}
}