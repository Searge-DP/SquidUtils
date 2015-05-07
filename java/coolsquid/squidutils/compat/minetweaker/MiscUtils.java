/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.compat.minetweaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.apache.logging.log4j.message.SimpleMessage;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.helpers.server.chat.ChatMessage;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.objects.CrashCallable;
import coolsquid.squidapi.util.version.UpdateManager;
import coolsquid.squidapi.util.version.VersionContainer;
import coolsquid.squidutils.SquidUtils;
import cpw.mods.fml.common.FMLCommonHandler;

@ZenClass("mods.squidutils.Misc")
public class MiscUtils {

	@ZenMethod
	public static void registerCrashCallable(String label, String message) {
		SquidAPI.COMMON.registerCallable(new CrashCallable(label, message));
	}

	@ZenMethod
	public static void registerShutdownMessage(String message) {
		SquidAPI.COMMON.registerShutdownMessage(new SimpleMessage(message));
	}

	@ZenMethod
	public static void registerShutdownMessages(String[] messages) {
		for (String message: messages) {
			SquidAPI.COMMON.registerShutdownMessage(new SimpleMessage(message));
		}
	}

	@ZenMethod
	public static void registerLoginMessage(String sender, String message, String format) {
		SquidAPI.COMMON.registerLoginMessage(new ChatMessage("<", sender, "> ", message).setColor(EnumChatFormatting.getValueByName(format)));
	}

	@ZenMethod
	public static void registerLoginMessage(String sender, String message) {
		SquidAPI.COMMON.registerLoginMessage(new ChatMessage("<", sender, "> ", message));
	}

	@ZenMethod
	public static void removeCrashCallables(String[] labels) {
		for (String label: labels) {
			Utils.removeCrashCallable(label);
		}
	}

	@ZenMethod
	public static String getOutdatedMods() {
		StringBuilder a = new StringBuilder();
		for (VersionContainer b: UpdateManager.INSTANCE.getOutdatedMods()) {
			a.append(b.getMod().getName());
			a.append(", ");
		}
		return a.substring(0, a.length() - 2);
	}

	@SuppressWarnings("unchecked")
	@ZenMethod
	public static <E> Class<E> findClass(String name) {
		return (Class<E>) Utils.getClass(name);
	}

	@ZenMethod
	public static void disablePhysics() {
		SquidUtils.COMMON.getPhysics().clear();
	}

	@ZenMethod
	public static void setDefaultSetting(String name, double value) {
		Options setting = Options.valueOf(name);
		Minecraft.getMinecraft().gameSettings.setOptionFloatValue(setting, (float) value);
	}

	@ZenMethod
	public static void setDefaultSetting(String name, int value) {
		Options setting = Options.valueOf(name);
		Minecraft.getMinecraft().gameSettings.setOptionValue(setting, value);
	}

	@ZenMethod
	public static void crash() {
		FMLCommonHandler.instance().raiseException(new RuntimeException(), "debug", true);
	}

	@ZenMethod
	public static void disableGameOverlays(String[] names) {
		for (String name: names) {
			SquidUtils.COMMON.disableOverlay(ElementType.valueOf(name));
		}
	}
}