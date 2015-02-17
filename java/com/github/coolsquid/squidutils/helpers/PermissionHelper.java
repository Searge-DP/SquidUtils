package com.github.coolsquid.squidutils.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraftforge.event.world.WorldEvent.Save;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.logging.Logger;
import com.github.coolsquid.squidutils.command.CommandPermissions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PermissionHelper {
	
	private static final HashMap<UUID, HashSet<String>> permissions = new HashMap<UUID, HashSet<String>>();
	private static final Logger filewriter = new Logger("config/SquidUtils", "permissions.txt", "Permissions:");
	
	public static void init() {
		SquidAPI.commands.add(new CommandPermissions());
		ArrayList<String> permissions = FileHelper.readFile("config/SquidUtils", "permissions.txt");
		for (String a: permissions) {
			HashSet<String> set = new HashSet<String>();
			String[] s = a.split(" ");
			String[] s2 = s[1].split(";");
			for (String a2: s2) {
				set.add(a2);
			}
			PermissionHelper.permissions.put(UUID.fromString(s[0]), set);
		}
	}
	
	@SubscribeEvent
	public void save(Save event) {
		for (Entry<UUID, HashSet<String>> a: permissions.entrySet()) {
			UUID id = a.getKey();
			String s = id.toString() + " ";
			for (Object a2: a.getValue().toArray()) {
				s = s + a2 + ";";
			}
			filewriter.log(s);
		}
		filewriter.save();
	}
	
	public static HashSet<String> getPermissions(UUID id) {
		if (!permissions.containsKey(id)) {
			permissions.put(id, new HashSet<String>());
		}
		return permissions.get(id);
	}
	
	public static boolean hasPermission(UUID id, String perm) {
		return getPermissions(id).contains(perm) || getPermissions(id).contains("*");
	}
	
	public static void addPermission(UUID id, String perm) {
		getPermissions(id).add(perm);
	}
	
	public static void removePermission(UUID id, String perm) {
		getPermissions(id).remove(perm);
	}
}