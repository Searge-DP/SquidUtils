/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraftforge.event.world.WorldEvent.Save;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.logging.ILogger;
import com.github.coolsquid.squidapi.logging.Logger;
import com.github.coolsquid.squidutils.command.CommandPermissions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PermissionHelper {

	public static final PermissionHelper INSTANCE = new PermissionHelper();

	private PermissionHelper() {
		
	}

	private final HashMap<UUID, HashSet<String>> permissions = new HashMap<UUID, HashSet<String>>();
	private final ILogger filewriter = new Logger(new File("./config/SquidUtils/permissions.txt"));
	
	public void init() {
		SquidAPI.commands.add(new CommandPermissions());
		List<String> permissions = FileHelper.readFile("config/SquidUtils", "permissions.txt");
		for (String a: permissions) {
			HashSet<String> set = new HashSet<String>();
			String[] s = a.split(" ");
			String[] s2 = s[1].split(";");
			for (String a2: s2) {
				set.add(a2);
			}
			this.permissions.put(UUID.fromString(s[0]), set);
		}
	}
	
	@SubscribeEvent
	public void save(Save event) {
		//TODO optimize
		for (Entry<UUID, HashSet<String>> a: this.permissions.entrySet()) {
			UUID id = a.getKey();
			String s = id.toString() + " ";
			for (Object a2: a.getValue().toArray()) {
				s = s + a2 + ";";
			}
			this.filewriter.log(s);
		}
	}
	
	public HashSet<String> getPermissions(UUID id) {
		if (!this.permissions.containsKey(id)) {
			this.permissions.put(id, new HashSet<String>());
		}
		return this.permissions.get(id);
	}
	
	public boolean hasPermission(UUID id, String perm) {
		return this.getPermissions(id).contains(perm) || this.getPermissions(id).contains("*");
	}
	
	public void addPermission(UUID id, String perm) {
		this.getPermissions(id).add(perm);
	}
	
	public void removePermission(UUID id, String perm) {
		this.getPermissions(id).remove(perm);
	}
}