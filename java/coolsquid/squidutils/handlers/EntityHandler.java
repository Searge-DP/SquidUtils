/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.handlers;

import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import coolsquid.squidapi.helpers.PotionHelper;
import coolsquid.squidapi.util.EffectInfo;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.util.EntityInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

	public static final Set<Class<? extends Entity>> disable = Sets.newHashSet();
	public static final Map<Class<Entity>, EntityInfo> properties = Maps.newHashMap();

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (disable.contains(event.entity.getClass())) {
			event.setCanceled(true);
			return;
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
			if (entity instanceof EntityCreeper && !props.avoidCats) {
				for (int a = 0; a < living.tasks.taskEntries.size(); a++) {
					Object ai = living.tasks.taskEntries.get(a);
					if (ai instanceof EntityAIAvoidEntity) {
						living.tasks.taskEntries.remove(a);
					}
				}
			}
		}
		else if (entity instanceof EntityLightningBolt) {
			EntityLightningBolt bolt = (EntityLightningBolt) entity;
			bolt.boltLivingTime *= ModConfigHandler.INSTANCE.boltLivingTimeMultiplier;
		}
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (SquidUtils.COMMON.getDisabledDamageSources().contains(event.source)) {
			event.setCanceled(true);
		}
	}
}