/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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
				this.handleString(message.key, message.getStringValue(), message.getSender());
			}
			else if (message.isItemStackMessage()) {
				this.handleItemStack(message.key, message.getItemStackValue(), message.getSender());
			}
			else if (message.isNBTMessage()) {
				this.handleNBT(message.key, message.getNBTValue(), message.getSender());
			}
		} catch (Throwable t) {
			SquidUtils.instance().error(message.key);
			SquidUtils.instance().error(message.getSender());
			SquidUtils.instance().error(t);
		}
	}

	private void handleNBT(String key, NBTTagCompound tag, String sender) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(tag.getString("class"));
		String field = tag.getString("field");
		Object object = ReflectionHelper.in(clazz).field(field, field).get();
		if (key.equals("registerDamageSource")) {
			registerDamageSource(sender, (DamageSource) object);
		}
		else if (key.equals("registerMaterial")) {
			registerMaterial(sender, tag.getString("name"), (Material) object);
		}
	}

	private void handleItemStack(String key, ItemStack stack, String sender) {
		if (key.equals("registerMaterial")) {
			registerMaterial(sender, stack.getDisplayName(), Block.getBlockFromItem(stack.getItem()).blockMaterial);
		}
	}

	public void handleString(String key, String message, String sender) throws ClassNotFoundException {
		String[] values = message.split(" ");
		Object object = ReflectionHelper.in(Class.forName(values[0])).field(values[1], values[1]).get();
		if (key.equals("registerDamageSource")) {
			registerDamageSource(sender, (DamageSource) object);
		}
		else if (key.equals("registerMaterial")) {
			registerMaterial(sender, values[2], (Material) object);
		}
	}

	private static void registerDamageSource(String mod, DamageSource source) {
		SquidUtils.API.damageSources.register(mod + ':' + source.damageType, source);
	}

	private static void registerMaterial(String mod, String name, Material material) {
		SquidUtils.API.materials.register(mod + ':' + name, material);
	}
}