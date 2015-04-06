/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import coolsquid.squidapi.helpers.PotionHelper;
import coolsquid.squidapi.util.EffectInfo;
import coolsquid.squidutils.api.ScriptingAPI.IEventTrigger;
import coolsquid.squidutils.config.GeneralConfigHandler;
import coolsquid.squidutils.util.EntityInfo;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler implements IEventTrigger {
	
	public static final Set<Class<? extends Entity>> disable = Sets.newHashSet();
	
	public static final List<EventInfo> info = new ArrayList<EventInfo>();
	public static final Map<Class<Entity>, EntityInfo> properties = Maps.newHashMap();
	
	@Override
	public List<EventInfo> info() {
		return info;
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (disable.contains(event.entity.getClass())) {
			event.setCanceled(true);
		}
		if (event.entity instanceof EntityLivingBase) {
			for (EventInfo a: info) {
				if (EventEffectHelper.isCorrectType((EntityLivingBase) event.entity, (String) a.values.get("entitytype"))) {
					EventEffectHelper.performEffects(a, (EntityLivingBase) event.entity);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event) {
		Entity entity = event.entity;
		if (properties.containsKey(entity.getClass()) && entity instanceof EntityLiving) {
			EntityLiving living = (EntityLiving) entity;
			EntityInfo props = properties.get(entity.getClass());
			if (props.fireResistance != -1 && props.fireResistance != living.fireResistance) {
				living.fireResistance = props.fireResistance;
			}
			if (props.isImmuneToFire && props.isImmuneToFire != living.isImmuneToFire) {
				living.isImmuneToFire = props.isImmuneToFire;
			}
			if (props.invisible) {
				living.setInvisible(true);
			}
			if (props.absorptionAmount != -1 && props.absorptionAmount != living.field_110151_bq) {
				living.setAbsorptionAmount(props.absorptionAmount);
			}
			if (props.name != "" && props.name != living.getCustomNameTag()) {
				living.setCustomNameTag(props.name);
			}
			if (!props.equipment.isEmpty()) {
				for (int a = 0; a < props.equipment.size(); a++) {
					living.setCurrentItemOrArmor(a, props.equipment.get(a));
				}
			}
			if (!props.effects.isEmpty()) {
				for (EffectInfo a: props.effects) {
					living.addPotionEffect(PotionHelper.newEffect(a));
				}
			}
		}
		else if (entity instanceof EntityLightningBolt) {
			EntityLightningBolt bolt = (EntityLightningBolt) entity;
			bolt.boltLivingTime *= GeneralConfigHandler.SETTINGS.getInt("boltLivingTimeMultiplier");
		}
	}
}