/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.api.impl;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import coolsquid.lib.util.ReflectionHelper;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

public class IMCHandler {

	/**
	 * Handles an IMC event.
	 *
	 * @param event the event
	 */
	public void handleIMCEvent(IMCEvent event) {
		for (IMCMessage message: event.getMessages()) {
			this.handleMessage(message);
		}
	}

	/**
	 * Handles an IMC message.
	 *
	 * @param message the message
	 */
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
			SquidUtils.log.error(message.key);
			SquidUtils.log.error(message.getSender());
			SquidUtils.log.error(t);
		}
	}

	private void handleNBT(String key, NBTTagCompound tag, String sender) throws ReflectiveOperationException {
		Class<?> clazz = Class.forName(tag.getString("class"));
		String field = tag.getString("field");
		Object object = ReflectionHelper.getPrivateStaticValue(clazz, field);
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

	private void handleString(String key, String message, String sender) throws ReflectiveOperationException {
		String[] values = message.split(" ");
		Object object = ReflectionHelper.getPrivateStaticValue(Class.forName(values[0]), values[1]);
		if (key.equals("registerDamageSource")) {
			registerDamageSource(sender, (DamageSource) object);
		}
		else if (key.equals("registerMaterial")) {
			registerMaterial(sender, values[2], (Material) object);
		}
	}

	private static void registerDamageSource(String mod, DamageSource source) {
		((SquidUtilsAPIImpl) SquidUtils.API).damageSources.register(mod + ':' + source.damageType, source);
	}

	private static void registerMaterial(String mod, String name, Material material) {
		((SquidUtilsAPIImpl) SquidUtils.API).materials.register(mod + ':' + name, material);
	}
}