/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting;

import java.io.File;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.helpers.FileHelper;
import coolsquid.squidapi.util.EffectInfo;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidapi.util.math.IntUtils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.util.script.EventInfo;

public class ScriptHandler {

	public static final ScriptHandler INSTANCE = new ScriptHandler();

	private ScriptHandler() {

	}

	public boolean onCraft;
	public boolean onSmelt;
	public boolean onHurt;
	public boolean onToss;
	public boolean onTeleport;
	public boolean onHeal;
	public boolean onStarve;
	public boolean onEntityJoin;
	public boolean onAchievement;
	public boolean onHungerRegen;
	public boolean onInteraction;
	public boolean onExplosion;
	public boolean onCommand;
	public boolean onChat;

	public boolean permissions;

	public void init() {
		for (File file: FileHelper.getFilesInDir("./config/SquidUtils")) {
			if (!file.getName().endsWith(".script")) {
				continue;
			}
			SquidUtils.instance().info("Found scripting file!");
			SquidUtils.instance().info(file.getName());
			for (String line: IOUtils.newReader(file)) {
				this.load(line);
			}
		}
	}

	public void load(String line) {
		try {
			if (!line.startsWith("#")) {
				String[] s2 = line.split(" ");
				String type = s2[0];
				if (!type.equals("on")) {
					Builder<String, String> builder = ImmutableMap.builder();
					for (int b = 2; b < s2.length; b++) {
						String[] aa = s2[b].split("=");
						if (aa.length == 1) {
							builder.put(aa[0], "");
						}
						else if (aa.length == 2) {
							builder.put(aa[0], aa[1]);
						}
					}
					SquidUtils.API.getScripting().getCommands().get(type).getSubcommands().get(s2[1]).run(builder.build());
				}
				else {
					Builder<String, Object> builder = ImmutableMap.builder();
					String trigger = s2[1];
					if (trigger.equals("craft")) {
						this.onCraft = true;
					}
					else if (trigger.equals("smelt")) {
						this.onSmelt = true;
					}
					else if (trigger.equals("hurt")) {
						this.onHurt = true;
					}
					else if (trigger.equals("toss")) {
						this.onToss = true;
					}
					else if (trigger.equals("teleport")) {
						this.onTeleport = true;
					}
					else if (trigger.equals("heal")) {
						this.onHeal = true;
					}
					else if (trigger.equals("entityjoin")) {
						this.onEntityJoin = true;
					}
					else if (trigger.equals("achievement")) {
						this.onAchievement = true;
					}
					else if (trigger.equals("hungerregen")) {
						this.onHungerRegen = true;
					}
					else if (trigger.equals("interaction")) {
						this.onInteraction = true;
					}
					else if (trigger.equals("explosion")) {
						this.onExplosion = true;
					}
					else if (trigger.equals("command")) {
						this.onCommand = true;
					}
					else if (trigger.equals("chat")) {
						this.onChat = true;
					}

					builder.put("minchance", 1);
					builder.put("maxchance", 1);
					builder.put("minhealth", -1F);
					builder.put("maxhealth", Float.MAX_VALUE);
					builder.put("minarmor", -1);
					builder.put("maxarmor", Integer.MAX_VALUE);

					for (int b = 2; b < s2.length; b++) {
						String[] aa = s2[b].split("=");
						String key = aa[0];
						if (key.matches("(requiredperm|oppositeperm)")) {
							this.permissions = true;
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
							else if (key.matches("(minchance|maxchance|minarmor|maxarmor|duration|foodlevel|experience)")) {
								value = IntUtils.parseInt((String) value);
							}
							else if (key.equals("effect")) {
								String[] args = ((String) value).split(":");
								value = new EffectInfo(IntUtils.parseInt(args[0]), IntUtils.parseInt(args[1]), IntUtils.parseInt(args[2]));
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
					SquidUtils.API.getScripting().getTriggers().get(trigger).info().add(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MiscLib.LOGGER.log(e);
			SquidAPI.COMMON.registerLoginMessage(StringUtils.newString(e.getClass().getName(), ". See SquidAPI.log for more information."));
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