/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.scripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.util.Integers;
import com.github.coolsquid.squidapi.util.StringParser;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.util.script.EffectInfo;
import com.github.coolsquid.squidutils.util.script.EventInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ScriptHandler {
	
	public static boolean onCraft;
	public static boolean onSmelt;
	public static boolean onHurt;
	public static boolean onToss;
	public static boolean onTeleport;
	public static boolean onHeal;
	public static boolean onStarve;
	public static boolean onEntityJoin;
	public static boolean onAchievement;
	public static boolean onHungerRegen;
	public static boolean onInteraction;
	public static boolean onExplosion;
	public static boolean onCommand;
	public static boolean onChat;
	
	public static boolean permissions;
	
	private static List<String> list;
	
	private static List<String> getScripts() {
		if (list == null) {
			list = new ArrayList<String>();
			for (File file: FileHelper.getFilesInDir("./config/SquidUtils")) {
				if (!file.getName().endsWith(".script")) {
					continue;
				}
				LogHelper.info("Found scripting file!");
				LogHelper.info(file.getName());
				try {
					BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					while (true) {
						String s = r.readLine();
						if (s == null) {
							break;
						}
						list.add(s);
					}
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public static void init() throws Exception {
		for (int a = 0; a < getScripts().size(); a++) {
			String s = getScripts().get(a);
			if (!s.startsWith("#")) {
				String[] s2 = s.split(" ");
				String type = s2[0];
				if (!type.equals("on")) {
					Builder<String, String> builder = ImmutableMap.builder();
					for (int b = 2; b < s2.length; b++) {
						String[] aa = s2[b].split("=");
						builder.put(aa[0], aa[1]);
					}
					ScriptingAPI.getCommands().get(type).getSubcommands().get(s2[1]).run(builder.build());
				}
				else {
					Builder<String, Object> builder = ImmutableMap.builder();
					String trigger = s2[1];
					if (trigger.equals("craft")) {
						onCraft = true;
					}
					else if (trigger.equals("smelt")) {
						onSmelt = true;
					}
					else if (trigger.equals("hurt")) {
						onHurt = true;
					}
					else if (trigger.equals("toss")) {
						onToss = true;
					}
					else if (trigger.equals("teleport")) {
						onTeleport = true;
					}
					else if (trigger.equals("heal")) {
						onHeal = true;
					}
					else if (trigger.equals("entityjoin")) {
						onEntityJoin = true;
					}
					else if (trigger.equals("achievement")) {
						onAchievement = true;
					}
					else if (trigger.equals("hungerregen")) {
						onHungerRegen = true;
					}
					else if (trigger.equals("interaction")) {
						onInteraction = true;
					}
					else if (trigger.equals("explosion")) {
						onExplosion = true;
					}
					else if (trigger.equals("command")) {
						onCommand = true;
					}
					else if (trigger.equals("chat")) {
						onChat = true;
					}
					
					builder.put("minchance", 1);
					builder.put("maxchance", 1);
					builder.put("minhealth", -1);
					builder.put("maxhealth", Integer.MAX_VALUE);
					builder.put("minarmor", -1);
					builder.put("maxarmor", Integer.MAX_VALUE);
					
					for (int b = 2; b < s2.length; b++) {
						String[] aa = s2[b].split("=");
						String key = aa[0];
						if (key.matches("(requiredperm|oppositeperm)")) {
							permissions = true;
						}
						if (aa.length == 1) {
							builder.put(key, new Object());
						}
						else if (aa.length == 2) {
							Object value = aa[1];
							if (key.equals("item")) {
								value = StringParser.parseItem((String) value);
							}
							else if (key.matches("(size|minhealth|maxhealth|damageamount)")) {
								value = Float.parseFloat((String) value);
							}
							else if (key.matches("(minchance|maxchance|minarmor|maxarmor|fireduration|foodlevel|experience)")) {
								value = Integers.parseInt((String) value);
							}
							else if (key.equals("effect")) {
								String[] args = ((String) value).split(":");
								value = new EffectInfo(Integers.parseInt(args[0]), Integers.parseInt(args[1]), Integers.parseInt(args[2]));
							}
							else if (key.equals("blocktoplace")) {
								value = StringParser.parseBlock((String) value);
							}
							else if (key.equals("item")) {
								value = StringParser.parseItem((String) value);
							}
							builder.put(key, value);
						}
					}
					EventInfo info = new EventInfo();
					info.values = builder.build();
					ScriptingAPI.getTriggers().get(trigger).info().add(info);
				}
			}
		}
	}
	
	/*public static void postInit() throws Exception {
		for (int a = 0; a < getScripts().size(); a++) {
			String s = getScripts().get(a);
			if (!s.startsWith("#")) {
				String[] s2 = s.split(" ");
				String type = s2[0];
				Builder<String, String> builder = ImmutableMap.builder();
				for (int b = 2; b < s2.length; b++) {
					String[] aa = s2[b].split("=");
					builder.put(aa[0], aa[1]);
				}
				ScriptingAPI.getCommands().get(type).getSubcommands().get(s2[1]).run(builder.build());
			}
		}
	}*/
}