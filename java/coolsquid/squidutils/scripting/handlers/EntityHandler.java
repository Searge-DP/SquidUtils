/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import coolsquid.squidutils.api.scripting.IEventTrigger;
import coolsquid.squidutils.scripting.EventEffectHelper;
import coolsquid.squidutils.util.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler implements IEventTrigger {

	public static final List<EventInfo> info = new ArrayList<EventInfo>();

	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		Entity entity = event.entity;
		if (entity instanceof EntityLivingBase) {
			for (EventInfo a: info) {
				if (EventEffectHelper.isCorrectType((EntityLivingBase) entity, (String) a.values.get("entitytype"))) {
					EventEffectHelper.performEffects(a, (EntityLivingBase) entity);
				}
			}
		}
	}
}