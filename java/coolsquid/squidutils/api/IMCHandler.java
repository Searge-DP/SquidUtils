/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import coolsquid.squidapi.reflection.ReflectionHelper;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

public class IMCHandler {

	public void handleIMCEvent(IMCEvent event) {
		for (IMCMessage message: event.getMessages()) {
			this.handleMessage(message);
		}
	}

	public void handleMessage(IMCMessage message) {
		try {
			if (message.isStringMessage()) {
				String[] values = message.getStringValue().split(" ");
				Object object = ReflectionHelper.in(Class.forName(values[0])).field(values[1], values[1]).get();
				if (message.key.equals("registerDamageSource")) {
					SquidUtils.API.registerDamageSourceWithIMC(message.getSender(), (DamageSource) object);
				}
				else if (message.key.equals("registerMaterial")) {
					SquidUtils.API.registerMaterialWithIMC(message.getSender(), values[2], (Material) object);
				}
			}
			else if (message.isItemStackMessage()) {
				if (message.key.equals("registerMaterial")) {
					SquidUtils.API.registerMaterialWithIMC(message.getSender(), message.getItemStackValue().getDisplayName(), Block.getBlockFromItem(message.getItemStackValue().getItem()).blockMaterial);
				}
			}
			else if (message.isNBTMessage()) {
				NBTTagCompound tag = message.getNBTValue();
				Class<?> clazz = Class.forName(tag.getString("class"));
				String field = tag.getString("field");
				Object object = ReflectionHelper.in(clazz).field(field, field).get();
				if (message.key.equals("registerDamageSource")) {
					SquidUtils.API.registerDamageSourceWithIMC(message.getSender(), (DamageSource) object);
				}
				else if (message.key.equals("registerMaterial")) {
					SquidUtils.API.registerMaterialWithIMC(message.getSender(), tag.getString("name"), (Material) object);
				}
			}
		} catch (Throwable t) {
			SquidUtils.instance().error(t);
		}
	}
}