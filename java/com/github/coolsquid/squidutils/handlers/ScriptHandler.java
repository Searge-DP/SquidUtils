/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.block.BlockBasic;
import com.github.coolsquid.squidapi.creativetab.ITab;
import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.helpers.RegistryHelper;
import com.github.coolsquid.squidapi.item.ItemBasic;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.command.CommandInfo;
import com.github.coolsquid.squidutils.command.CommandWeb;
import com.github.coolsquid.squidutils.util.EffectInfo;
import com.github.coolsquid.squidutils.util.EventInfo;
import com.github.coolsquid.starstones.block.BlockMeteorBase;
import com.github.coolsquid.starstones.block.MeteorType;
import com.github.coolsquid.starstones.creativetab.ModCreativeTabs;

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
	
	public static void init() {
		ArrayList<String> list = FileHelper.readFile("config/SquidUtils", ("script.txt"));
		for (int a = 0; a < list.size(); a++) {
			String s = list.get(a);
			if (!s.startsWith("#")) {
				String[] s2 = s.split(" ");
				String type = s2[0];
				if (type.equals("block")) {
					Block block = (Block) Block.blockRegistry.getObject(s2[2]);
					if (s2[1].equals("hardness")) block.setHardness(Float.parseFloat(s2[3]));
					else if (s2[1].equals("slipperiness")) block.slipperiness = Float.parseFloat(s2[3]);
					else if (s2[1].equals("resistance")) block.setResistance(Float.parseFloat(s2[3]));
					else if (s2[1].equals("texture")) block.setBlockTextureName(s2[3]);
					else if (s2[1].equals("create")) {
						if (s2[2].equals("meteor")) {
							MeteorType t;
							if (s2[4].equals("STONE")) t = MeteorType.STONE;
							else if (s2[4].equals("END")) t = MeteorType.END;
							else if (s2[4].equals("ICE")) t = MeteorType.ICE;
							else if (s2[4].equals("OBSIDIAN")) t = MeteorType.OBSIDIAN;
							else if (s2[4].equals("HELL")) t = MeteorType.HELL;
							else t = null;
							new BlockMeteorBase(s2[3], t).setCreativeTab(ModCreativeTabs.tab);
						}
						else if (s2[2].equals("glass")) {
							BlockGlass b = new BlockGlass(Material.glass, false);
							b.setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(s2[3]).setBlockName(s2[3]);
							RegistryHelper.registerBlock(b, s2[3]);
						}
						else if (s2[2].equals("basic")) {
							BlockBasic b = new BlockBasic(s2[3]);
							b.setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(s2[3]);
						}
					}
				}
				else if (type.equals("item")) {
					Item item = (Item) Item.itemRegistry.getObject(s2[2]);
					if (s2[1].equals("stacksize")) item.setMaxStackSize(Integer.parseInt(s2[3]));
					else if (s2[1].equals("maxdamage")) item.setMaxDamage(Integer.parseInt(s2[3]));
					else if (s2[1].equals("create")) {
						if (s2[2].equals("food")) {
							RegistryHelper.registerItem(new ItemFood(Integer.parseInt(s2[3]), Float.parseFloat(s2[4]), Boolean.parseBoolean(s2[5])).setTextureName(s2[6]).setCreativeTab(CreativeTabs.tabFood).setUnlocalizedName(s2[6]), s2[6]);
						}
						else if (s2[2].equals("basic")) {
							new ItemBasic(s2[3]).setCreativeTab(CreativeTabs.tabMaterials);
						}
					}
				}
				else if (type.equals("command")) {
					if (s2[1].equals("disable")) CommandHandler.commandsToDisable.add(s2[2]);
					else if (s2[1].equals("create")) {
						if (s2[2].equals("info")) {
							String s7 = s2[4];
							String s8 = s2[5];
							CommandInfo command = new CommandInfo(s2[3], s7.replace("_", " "), s8.replace("_", " "));
							SquidAPI.commands.add(command);
						}
						else if (s2[2].equals("web")) {
							String s7 = s2[4];
							String s8 = s2[5];
							CommandWeb command = new CommandWeb(s2[3], s7.replace("_", " "), s8);
							SquidAPI.commands.add(command);
						}
					}
				}
				else if (type.equals("tab")) {
					if (s2[1].equals("create")) {
						ITab tab;
						if (Block.blockRegistry.containsKey(s2[3])) {
							tab = new ITab(s2[2], Item.getItemFromBlock((Block) Block.blockRegistry.getObject(s2[3])));
						}
						else if (Item.itemRegistry.containsKey(s2[3])) {
							tab = new ITab(s2[2], (Item) Item.itemRegistry.getObject(s2[3]));
						}
						else {
							tab = null;
						}
						for (int b = 4; b < s2.length; b++) {
							if (Block.blockRegistry.containsKey(s2[b])) ((Block) Block.blockRegistry.getObject(s2[b])).setCreativeTab(tab);
							else if (Item.itemRegistry.containsKey(s2[b])) ((Item) Item.itemRegistry.getObject(s2[b])).setCreativeTab(tab);
						}
					}
				}
				else if (type.equals("recipe")) {
					if (s2[1].equals("create")) {
						if (s2[2].equals("explosive")) {
							RegistryHelper.addExplosionRecipe(Item.itemRegistry.getObject(s2[3]), new ItemStack((Item) Item.itemRegistry.getObject(s2[4])), Float.parseFloat(s2[5]));
						}
						else if (s2[2].equals("shapeless")) {
							RegistryHelper.addShapelessRecipe(new ItemStack((Item) Item.itemRegistry.getObject(s2[4])), new Item[] {(Item) Item.itemRegistry.getObject(s2[3])});
						}
						else if (s2[2].equals("furnace")) {
							RegistryHelper.addSmelting((Item) Item.itemRegistry.getObject(s2[3]), new ItemStack((Item) Item.itemRegistry.getObject(s2[4])));
						}
					}
				}
				else if (type.equals("on")) {
					String trigger = s2[1];
					EventInfo info = new EventInfo();
					
					String action = s2[2];
					
					int e = 3;
					int f = 4;
					int g = 5;
					
					String[] if2 = s2[2].split(";");
					
					if (if2[0].contains(":")) {
						action = s2[3];
						e++;
						f++;
						g++;
					}
					
					float minamount = Float.MIN_VALUE;
					float maxamount = Float.MAX_VALUE;
					String damagetype = "";
					Item item = null;
					for (int h = 0; h < if2.length; h++) {
						String arg = if2[h];
						if (arg.startsWith("minamount:")) {
							String ss = arg.replace("minamount:", "");
							if (!ss.equals("*")) minamount = Float.parseFloat(ss);
						}
						else if (arg.startsWith("maxamount:")) {
							String ss = arg.replace("maxamount:", "");
							if (!ss.equals("*")) maxamount = Float.parseFloat(ss);
						}
						else if (arg.startsWith("minhealth:")) {
							String ss = arg.replace("minhealth:", "");
							if (!ss.equals("*")) info.setMinHealth(Float.parseFloat(ss));
						}
						else if (arg.startsWith("maxhealth:")) {
							String ss = arg.replace("maxhealth:", "");
							if (!ss.equals("*")) info.setMaxHealth(Float.parseFloat(ss));
						}
						else if (arg.startsWith("item:")) {
							String ss = arg.replace("item:", "");
							if (!ss.equals("*")) item = (Item) Item.itemRegistry.getObject(ss);
							info.setItem(item);
						}
						else if (arg.startsWith("minarmor:")) {
							String ss = arg.replace("minarmor:", "");
							if (!ss.equals("*")) info.setMinarmor(Integer.parseInt(ss));
						}
						else if (arg.startsWith("maxarmor:")) {
							String ss = arg.replace("maxarmor:", "");
							if (!ss.equals("*")) info.setMaxarmor(Integer.parseInt(ss));
						}
						else if (arg.startsWith("damagetype:")) {
							String ss = arg.replace("damagetype:", "");
							damagetype = ss;
						}
						else if (arg.startsWith("minchance:")) {
							String ss = arg.replace("minchance:", "");
							if (!ss.equals("*")) info.setMinchance(Integer.parseInt(ss));
						}
						else if (arg.startsWith("maxchance:")) {
							String ss = arg.replace("maxchance:", "");
							if (!ss.equals("*")) info.setMaxchance(Integer.parseInt(ss));
						}
						else if (arg.startsWith("entitytype:")) {
							String ss = arg.replace("entitytype:", "");
							info.setEntitytype(ss);
						}
						else if (ScriptingAPI.arguments.containsKey(arg.split(":")[0])) {
							String key = arg.split(":")[0];
							ScriptingAPI.arguments.get(key).run(arg.replace(key + ":", ""));
						}
					}
					info.setDamagetype(damagetype);
					info.setMinamount(minamount);
					info.setMaxamount(maxamount);
					
					if (action.equals("explode")) {
						info.setExplosionsize(Float.parseFloat(s2[e]));
					}
					else if (action.equals("damage")) {
						info.setDamageamount(Float.parseFloat(s2[e]));
					}
					else if (action.equals("applyeffect")) {
						info.addEffect(new EffectInfo(Integer.parseInt(s2[e]), Integer.parseInt(s2[f]), Integer.parseInt(s2[g])));
					}
					else if (action.equals("addexperience")) {
						info.setExperienceamount(Integer.parseInt(s2[e]));
					}
					else if (action.equals("cancel")) {
						info.setCancel(true);
					}
					else if (action.equals("sprint")) {
						info.setSprint(true);
					}
					else if (action.equals("setinvisible")) {
						info.setInvisible(true);
					}
					else if (ScriptingAPI.actions.containsKey(action)) {
						info.setAction(action);
						ScriptingAPI.actions.get(action).init(info);
					}
					
					if (trigger.equals("entityjoin")) {
						onEntityJoin = true;
						EntityJoinHandler.info.add(info);
					}
					else if (trigger.equals("hurt")) {
						onHurt = true;
						DamageHandler.info.add(info);
					}
					else if (trigger.equals("smelt")) {
						onSmelt = true;
						SmeltingHandler.info.add(info);
					}
					else if (trigger.equals("craft")) {
						onCraft = true;
						CraftingHandler.info.add(info);
					}
					else if (trigger.equals("toss")) {
						onToss = true;
						TossHandler.info.add(info);
					}
					else if (trigger.equals("heal")) {
						onHeal = true;
						HealingHandler.info.add(info);
					}
					else if (trigger.equals("teleport")) {
						onTeleport = true;
						TeleportationHandler.info.add(info);
					}
					else if (trigger.equals("starve")) {
						onStarve = true;
						FoodHandler.info.add(info);
					}
					else if (trigger.equals("achievement")) {
						onAchievement = true;
						AchievementHandler.info.add(info);
					}
					else if (trigger.equals("hungerregen")) {
						onHungerRegen = true;
						RegenHandler.info.add(info);
					}
					else if (trigger.equals("interaction")) {
						onInteraction = true;
						InteractionHandler.info.add(info);
					}
					else if (ScriptingAPI.triggers.containsKey(trigger)) {
						ScriptingAPI.triggers.get(trigger).info().add(info);
					}
				}
			}
		}
	}
}