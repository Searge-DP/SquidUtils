/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.block.BlockBasic;
import com.github.coolsquid.squidapi.compat.BotaniaCompat;
import com.github.coolsquid.squidapi.compat.Compat;
import com.github.coolsquid.squidapi.compat.RotaryCraftCompat;
import com.github.coolsquid.squidapi.compat.ThermalExpansionCompat;
import com.github.coolsquid.squidapi.creativetab.ITab;
import com.github.coolsquid.squidapi.helpers.BiomeHelper;
import com.github.coolsquid.squidapi.helpers.FileHelper;
import com.github.coolsquid.squidapi.helpers.FishingHelper;
import com.github.coolsquid.squidapi.helpers.RegistryHelper;
import com.github.coolsquid.squidapi.item.ItemBasic;
import com.github.coolsquid.squidapi.util.ContentRemover;
import com.github.coolsquid.squidapi.util.ContentRemover.ContentType;
import com.github.coolsquid.squidapi.util.Integers;
import com.github.coolsquid.squidapi.util.StringParser;
import com.github.coolsquid.squidapi.world.biome.BiomeBase;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.command.CommandCustom;
import com.github.coolsquid.squidutils.command.CommandInfo;
import com.github.coolsquid.squidutils.command.CommandWeb;
import com.github.coolsquid.squidutils.handlers.DropHandler.Chance;
import com.github.coolsquid.squidutils.handlers.DropHandler.Drop;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.util.script.EffectInfo;
import com.github.coolsquid.squidutils.util.script.EventInfo;
import com.github.coolsquid.starstones.block.BlockMeteorBase;
import com.github.coolsquid.starstones.block.MeteorType;
import com.github.coolsquid.starstones.creativetab.ModCreativeTabs;

import cpw.mods.fml.common.registry.VillagerRegistry;

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
				if (type.equals("block")) {
					if (s2[1].equals("create")) {
						if (s2[2].equals("meteor")) {
							MeteorType t;
							if (s2[4].equals("STONE")) t = MeteorType.STONE;
							else if (s2[4].equals("END")) t = MeteorType.END;
							else if (s2[4].equals("ICE")) t = MeteorType.ICE;
							else if (s2[4].equals("OBSIDIAN")) t = MeteorType.OBSIDIAN;
							else if (s2[4].equals("HELL")) t = MeteorType.HELL;
							else t = null;
							new BlockMeteorBase(s2[3], t).setCreativeTab(ModCreativeTabs.tab).setBlockTextureName("SquidUtils:" + s2[3]);
						}
						else if (s2[2].equals("glass")) {
							BlockGlass b = new BlockGlass(Material.glass, false);
							b.setCreativeTab(CreativeTabs.tabBlock).setBlockName(s2[3]);
							RegistryHelper.registerBlock(b, s2[3]);
						}
						else if (s2[2].equals("basic")) {
							BlockBasic b = new BlockBasic(s2[3]);
							b.setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("SquidUtils:" + s2[3]);
						}
					}
				}
				else if (type.equals("item")) {
					if (s2[1].equals("create")) {
						if (s2[2].equals("food")) {
							ItemFood food = new ItemFood(Integers.parseInt(s2[4]), Float.parseFloat(s2[5]), Boolean.parseBoolean(s2[6]));
							if (Boolean.parseBoolean(s2[7])) {
								food.setAlwaysEdible();
							}
							if (s2.length > 8) {
								food.setPotionEffect(Integers.parseInt(s2[8]), Integers.parseInt(s2[9]), Integers.parseInt(s2[10]), Float.parseFloat(s2[11]));
							}
							RegistryHelper.registerItem(food, s2[4]);
						}
						else if (s2[2].equals("basic")) {
							new ItemBasic(s2[3]).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("SquidUtils:" + s2[3]);
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
						else if (s2[2].equals("basic")) {
							CommandCustom command = new CommandCustom(s2[3], s2[4].replace("_", " "), Boolean.parseBoolean(s2[5]));
							SquidAPI.commands.add(command);
						}
					}
				}
				else if (type.equals("tab")) {
					if (s2[1].equals("create")) {
						ITab tab;
						if (Block.blockRegistry.containsKey(s2[3])) {
							tab = new ITab(s2[2], Item.getItemFromBlock(StringParser.parseBlock(s2[3])));
						}
						else if (Item.itemRegistry.containsKey(s2[3])) {
							tab = new ITab(s2[2], StringParser.parseItem(s2[3]));
						}
						else {
							tab = null;
						}
						for (int b = 4; b < s2.length; b++) {
							if (Block.blockRegistry.containsKey(s2[b])) (StringParser.parseBlock(s2[b])).setCreativeTab(tab);
							else if (Item.itemRegistry.containsKey(s2[b])) (StringParser.parseItem(s2[b])).setCreativeTab(tab);
						}
					}
				}
				else if (type.equals("recipe")) {
					if (s2[1].equals("create")) {
						if (s2[2].equals("explosive")) {
							RegistryHelper.addExplosionRecipe(Item.itemRegistry.getObject(s2[3]), new ItemStack(StringParser.parseItem(s2[4])), Float.parseFloat(s2[5]));
						}
						else if (s2[2].equals("shapeless")) {
							RegistryHelper.addShapelessRecipe(new ItemStack(StringParser.parseItem(s2[4])), new Item[] {StringParser.parseItem(s2[3])});
						}
						else if (s2[2].equals("furnace")) {
							RegistryHelper.addSmelting(StringParser.parseItem(s2[3]), new ItemStack(StringParser.parseItem(s2[4])));
						}
					}
					else if (s2[1].equals("remove")) {
						if (s2[2].equals("all") && !ContentRemover.isBlacklistedModLoaded()) {
							CraftingManager.getInstance().getRecipeList().clear();
						}
						else if (s2[2].equals("specific")) {
							ContentRemover.remove(s2[3], ContentType.RECIPE);
						}
					}
				}
				else if (type.equals("potion")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.POTION);
					}
				}
				else if (type.equals("enchantment")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.ENCHANTMENT);
					}
				}
				else if (type.equals("biome")) {
					if (s2[1].equals("remove")) {
						BiomeHelper.removeBiome(BiomeGenBase.getBiome(Integers.parseInt(s2[2])));
					}
					else if (s2[1].equals("create")) {
						BiomeBase biome = new BiomeBase(s2[2]);
						biome.topBlock = Block.getBlockFromName(s2[3]);
						biome.fillerBlock = Block.getBlockFromName(s2[4]);
						BiomeManager.addBiome(BiomeType.getType(s2[5]), new BiomeEntry(biome, Integers.parseInt(s2[6])));
					}
					else if (s2[1].equals("topblock")) {
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).topBlock = Block.getBlockFromName(s2[3]);
					}
					else if (s2[1].equals("fillerblock")) {
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).fillerBlock = Block.getBlockFromName(s2[3]);
					}
					else if (s2[1].equals("disablerain")) {
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).setDisableRain();
					}
					else if (s2[1].equals("enablesnow")) {
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).setEnableSnow();
					}
					else if (s2[1].equals("height")) {
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).setHeight(new Height(Float.parseFloat(s2[3]), Float.parseFloat(s2[4])));
					}
					else if (s2[1].equals("addflower")) {
						int weight = Integers.parseInt(s2[4]);
						int metadata = 0;
						if (s2.length == 6) {
							metadata = Integers.parseInt(s2[5]);
						}
						BiomeGenBase.getBiome(Integers.parseInt(s2[2])).addFlower(Block.getBlockFromName(s2[3]), metadata, weight);
					}
				}
				else if (type.equals("fish")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.FISH);
					}
					else if (s2[1].equals("add")) {
						FishingHelper.addFish(StringParser.parseItemStack(s2[2]), Integers.parseInt(s2[3]));
					}
				}
				else if (type.equals("junk")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.JUNK);
					}
					else if (s2[1].equals("add")) {
						FishingHelper.addJunk(StringParser.parseItemStack(s2[2]), Integers.parseInt(s2[3]));
					}
				}
				else if (type.equals("treasure")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.TREASURE);
					}
					else if (s2[1].equals("add")) {
						FishingHelper.addTreasure(StringParser.parseItemStack(s2[2]), Integers.parseInt(s2[3]));
					}
				}
				else if (type.equals("dungeonmob")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.DUNGEONMOB);
					}
					else if (s2[1].equals("add")) {
						DungeonHooks.addDungeonMob(s2[2], Integers.parseInt(s2[3]));
					}
				}
				else if (type.equals("chestgen")) {
					if (s2[1].equals("remove")) {
						StringBuilder builder = new StringBuilder();
						builder.append(s2[2]).append(";").append(s2[3]);
						ContentRemover.remove(builder.toString(), ContentType.CHESTGEN);
					}
					else if (s2[1].equals("add")) {
						ChestGenHooks.addItem(s2[2], new WeightedRandomChestContent(StringParser.parseItemStack(s2[3]), Integers.parseInt(s2[4]), Integers.parseInt(s2[5]), Integers.parseInt(s2[6])));
					}
				}
				else if (type.equals("villager")) {
					if (s2[1].equals("remove")) {
						ContentRemover.remove(s2[2], ContentType.PROFESSION);
					}
					else if (s2[1].equals("add")) {
						StringBuilder builder = new StringBuilder();
						builder.append("textures/entity/villager/").append(s2[3]).append(".png");
						VillagerRegistry.instance().registerVillagerId(Integers.parseInt(s2[2]));
						VillagerRegistry.instance().registerVillagerSkin(Integers.parseInt(s2[2]), new ResourceLocation("SquidUtils", builder.toString()));
					}
				}
				else if (type.equals("village")) {
					if (s2[1].equals("part")) {
						if (s2[2].equals("remove")) {
							
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
					
					float minamount = Float.MIN_VALUE;
					float maxamount = Float.MAX_VALUE;
					String damagetype = "";
					Item item = null;
					
					if (if2[0].contains(":")) {
						action = s2[3];
						e++;
						f++;
						g++;
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
								if (!ss.equals("*")) item = StringParser.parseItem(ss);
								info.setItem(item);
							}
							else if (arg.startsWith("minarmor:")) {
								String ss = arg.replace("minarmor:", "");
								if (!ss.equals("*")) info.setMinarmor(Integers.parseInt(ss));
							}
							else if (arg.startsWith("maxarmor:")) {
								String ss = arg.replace("maxarmor:", "");
								if (!ss.equals("*")) info.setMaxarmor(Integers.parseInt(ss));
							}
							else if (arg.startsWith("damagetype:")) {
								String ss = arg.replace("damagetype:", "");
								damagetype = ss;
							}
							else if (arg.startsWith("minchance:")) {
								String ss = arg.replace("minchance:", "");
								if (!ss.equals("*")) info.setMinchance(Integers.parseInt(ss));
							}
							else if (arg.startsWith("maxchance:")) {
								String ss = arg.replace("maxchance:", "");
								if (!ss.equals("*")) info.setMaxchance(Integers.parseInt(ss));
							}
							else if (arg.startsWith("entitytype:")) {
								String ss = arg.replace("entitytype:", "");
								info.setEntitytype(ss);
							}
							else if (arg.startsWith("chattrigger:")) {
								String ss = arg.replace("chattrigger:", "");
								info.setChattrigger(ss);
							}
							else if (arg.startsWith("commandname:")) {
								String ss = arg.replace("commandname:", "");
								info.setCommandname(ss);
							}
							else if (arg.startsWith("hasperm:")) {
								String ss = arg.replace("hasperm:", "");
								info.setRequiredperm(ss);
								permissions = true;
							}
							else if (arg.startsWith("missingperm:")) {
								String ss = arg.replace("missingperm:", "");
								info.setOppositeperm(ss);
								permissions = true;
							}
							else if (ScriptingAPI.getConditions().containsKey(arg.split(":")[0])) {
								String key = arg.split(":")[0];
								ScriptingAPI.getConditions().get(key).run(arg.replace(key + ":", ""));
							}
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
						info.setEffect(new EffectInfo(Integers.parseInt(s2[e]), Integers.parseInt(s2[f]), Integers.parseInt(s2[g])));
					}
					else if (action.equals("addexperience")) {
						info.setExperienceamount(Integers.parseInt(s2[e]));
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
					else if (action.equals("difficulty")) {
						info.setDifficulty(s2[e]);
					}
					else if (action.equals("cleareffects")) {
						info.setClearActiveEffects(true);
					}
					else if (action.equals("placeblock")) {
						info.setBlocktoplace(StringParser.parseBlock(s2[e]));
					}
					else if (action.equals("burn")) {
						info.setFireamount(Integers.parseInt(s2[e]));
					}
					else if (action.equals("sethunger")) {
						info.setFoodlevel(Integers.parseInt(s2[e]));
					}
					else if (ScriptingAPI.getActions().containsKey(action)) {
						info.setAction(action);
						ScriptingAPI.getActions().get(action).init(info);
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
					else if (trigger.equals("explosion")) {
						onExplosion = true;
						ExplosionHandler.info.add(info);
					}
					else if (trigger.equals("command")) {
						onCommand = true;
						CommandHandler.info.add(info);
					}
					else if (trigger.equals("chat")) {
						onChat = true;
						ServerChatHandler.info.add(info);
					}
					else if (ScriptingAPI.getTriggers().containsKey(trigger)) {
						if (s2.length >= e) {
							for (int gg = e; gg < s2.length; gg++) {
								String[] s3 = s2[gg].split(":");
								info.addCustomvalue(s3[0], s3[1]);
							}
						}
						ScriptingAPI.getTriggers().get(trigger).info().add(info);
					}
				}
			}
		}
	}
	
	public static void postInit() throws Exception {
		for (int a = 0; a < getScripts().size(); a++) {
			String s = getScripts().get(a);
			if (!s.startsWith("#")) {
				String[] s2 = s.split(" ");
				String type = s2[0];
				if (type.equals("block")) {
					Block block = StringParser.parseBlock(s2[2]);
					if (s2[1].equals("hardness")) block.setHardness(Float.parseFloat(s2[3]));
					else if (s2[1].equals("slipperiness")) block.slipperiness = Float.parseFloat(s2[3]);
					else if (s2[1].equals("resistance")) block.setResistance(Float.parseFloat(s2[3]));
					else if (s2[1].equals("texture")) block.setBlockTextureName(s2[3]);
					else if (s2[1].equals("drop")) {
						Block block2 = StringParser.parseBlock(s2[3]);
						if (s2[2].equals("remove")) {
							HashSet<Item> drops;
							if (DropHandler.dropstoremove.containsKey(block2)) {
								drops = DropHandler.dropstoremove.get(block2);
								for (int i = 4; i < s2.length; i++) {
									drops.add(StringParser.parseItem(s2[i]));
								}
							}
							else {
								drops = new HashSet<Item>();
								for (int i = 4; i < s2.length; i++) {
									drops.add(StringParser.parseItem(s2[i]));
								}
								DropHandler.dropstoremove.put(block2, drops);
							}
						}
						else if (s2[2].equals("add")) {
							ArrayList<Drop> drops;
							if (DropHandler.drops.containsKey(block2)) {
								drops = DropHandler.drops.get(block2);
								for (int i = 4; i < s2.length; i++) {
									String[] s4 = s2[i].split(";");
									drops.add(new Drop(StringParser.parseItem(s4[0]), Integers.parseInt(s4[1]), Integers.parseInt(s4[2]), new Chance(Integers.parseInt(s4[3]), Integers.parseInt(s4[4]))));
								}
							}
							else {
								drops = new ArrayList<Drop>();
								for (int i = 4; i < s2.length; i++) {
									String[] s4 = s2[i].split(";");
									drops.add(new Drop(StringParser.parseItem(s4[0]), Integers.parseInt(s4[1]), Integers.parseInt(s4[2]), new Chance(Integers.parseInt(s4[3]), Integers.parseInt(s4[4]))));
								}
								DropHandler.drops.put(block2, drops);
							}
						}
					}
				}
				else if (type.equals("item")) {
					Item item = StringParser.parseItem(s2[2]);
					if (s2[1].equals("stacksize")) item.setMaxStackSize(Integers.parseInt(s2[3]));
					else if (s2[1].equals("maxdamage")) item.setMaxDamage(Integers.parseInt(s2[3]));
				}
				else if (type.equals("compat")) {
					if (s2[1].equals("rotarycraft") && Compat.RotaryCraft.loadCompat()) {
						if (s2[2].equals("add")) {
							if (s2[3].equals("grinding")) {
								RotaryCraftCompat.addGrindingRecipe(new ItemStack(StringParser.parseItem(s2[4])), new ItemStack(StringParser.parseItem(s2[5])));
							}
							else if (s2[3].equals("worktable")) {
								if (s2[4].equals("shapeless")) {
									RotaryCraftCompat.addWorktableRecipe(new ItemStack(StringParser.parseItem(s2[5])), StringParser.parseItemStack(s2[6]));
								}
							}
							else if (s2[3].equals("compactor")) {
								RotaryCraftCompat.addCompactorRecipe(new ItemStack(StringParser.parseItem(s2[4])), StringParser.parseItemStack(s2[5]), Integers.parseInt(s2[6]), Integers.parseInt(s2[7]));
							}
						}
					}
					else if (s2[1].equals("thermalexpansion") && Compat.ThermalExpansion.loadCompat()) {
						if (s2[2].equals("add")) {
							if (s2[3].equals("pulverizer")) {
								ThermalExpansionCompat.addPulverizerRecipe(StringParser.parseItemStack(s2[4]), StringParser.parseItemStack(s2[5]), StringParser.parseItemStack(s2[6]), Integers.parseInt(s2[7]), Integers.parseInt(s2[8]));
							}
						}
					}
					else if (s2[1].equals("botania") && Compat.Botania.loadCompat()) {
						if (s2[2].equals("add")) {
							if (s2[3].equals("manainfusion")) {
								BotaniaCompat.registerManaInfusionRecipe(StringParser.parseItemStack(s2[5]), StringParser.parseInput(s2[4]), Integers.parseInt(s2[6]));
							}
							else if (s2[3].equals("manaalchemy")) {
								BotaniaCompat.registerManaAlchemyRecipe(StringParser.parseItemStack(s2[5]), StringParser.parseInput(s2[4]), Integers.parseInt(s2[6]));
							}
							else if (s2[3].equals("manaconjuration")) {
								BotaniaCompat.registerManaConjurationRecipe(StringParser.parseItemStack(s2[5]), StringParser.parseInput(s2[4]), Integers.parseInt(s2[6]));
							}
							else if (s2[3].equals("petal")) {
								Object[] inputs = new Object[9];
								for (int aa = 5; aa < s2.length; aa++) {
									inputs[aa - 5] = StringParser.parseInput(s2[aa]);
								}
								BotaniaCompat.registerPetalRecipe(StringParser.parseItemStack(s2[4]), inputs);
							}
							else if (s2[3].equals("elventrade")) {
								Object[] inputs = new Object[9];
								for (int aa = 5; aa < s2.length; aa++) {
									inputs[aa - 5] = StringParser.parseInput(s2[aa]);
								}
								BotaniaCompat.registerElvenTradeRecipe(StringParser.parseItemStack(s2[4]), inputs);
							}
						}
					}
				}
			}
		}
	}
}