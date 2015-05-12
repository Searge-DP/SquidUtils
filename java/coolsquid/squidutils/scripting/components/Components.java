/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.scripting.components;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Maps;

import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.block.BlockBase;
import coolsquid.squidapi.compat.BloodMagicCompat;
import coolsquid.squidapi.compat.BotaniaCompat;
import coolsquid.squidapi.compat.Compat;
import coolsquid.squidapi.compat.RailCraftCompat;
import coolsquid.squidapi.compat.RotaryCraftCompat;
import coolsquid.squidapi.compat.ThermalExpansionCompat;
import coolsquid.squidapi.creativetab.ITab;
import coolsquid.squidapi.helpers.AchievementHelper;
import coolsquid.squidapi.helpers.FishingHelper;
import coolsquid.squidapi.helpers.RegistryHelper;
import coolsquid.squidapi.item.ItemBase;
import coolsquid.squidapi.reflection.ReflectionHelper;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.StringUtils;
import coolsquid.squidapi.util.math.IntUtils;
import coolsquid.squidutils.SquidUtils;
import coolsquid.squidutils.api.scripting.IScriptSubcommand;
import coolsquid.squidutils.api.scripting.ScriptCommand;
import coolsquid.squidutils.command.CommandCustom;
import coolsquid.squidutils.command.CommandInfo;
import coolsquid.squidutils.command.CommandWeb;
import coolsquid.squidutils.handlers.DropHandler;
import coolsquid.squidutils.handlers.DropHandler.Chance;
import coolsquid.squidutils.handlers.DropHandler.Drop;
import coolsquid.starstones.block.BlockMeteorBase;
import coolsquid.starstones.block.MeteorType;
import coolsquid.starstones.creativetab.ModCreativeTabs;
import cpw.mods.fml.common.registry.VillagerRegistry;

@SuppressWarnings("deprecation")
public class Components {

	public static void init() {
		Map<String, IScriptSubcommand> blocksubcommands = Maps.newHashMap();
		blocksubcommands.put("create", new ScriptSubcommandBlockCreate());
		blocksubcommands.put("modify", new ScriptSubcommandBlockModify());
		SquidUtils.API.getScripting().addCommand("block", new ScriptCommand(blocksubcommands));

		Map<String, IScriptSubcommand> itemsubcommands = Maps.newHashMap();
		itemsubcommands.put("create", new ScriptSubcommandItemCreate());
		itemsubcommands.put("modify", new ScriptSubcommandItemModify());
		SquidUtils.API.getScripting().addCommand("item", new ScriptCommand(itemsubcommands));

		Map<String, IScriptSubcommand> commandsubcommands = Maps.newHashMap();
		commandsubcommands.put("create", new ScriptSubcommandCommandCreate());
		commandsubcommands.put("disable", new ScriptSubcommandCommandDisable());
		SquidUtils.API.getScripting().addCommand("command", new ScriptCommand(commandsubcommands));

		Map<String, IScriptSubcommand> tabsubcommands = Maps.newHashMap();
		tabsubcommands.put("create", new ScriptSubcommandTabCreate());
		SquidUtils.API.getScripting().addCommand("tab", new ScriptCommand(tabsubcommands));

		Map<String, IScriptSubcommand> recipesubcommands = Maps.newHashMap();
		recipesubcommands.put("create", new ScriptSubcommandRecipeCreate());
		recipesubcommands.put("remove", new ScriptSubcommandRecipeRemove());
		SquidUtils.API.getScripting().addCommand("recipe", new ScriptCommand(recipesubcommands));

		Map<String, IScriptSubcommand> biomesubcommands = Maps.newHashMap();
		biomesubcommands.put("create", new ScriptSubcommandBiomeCreate());
		biomesubcommands.put("modify", new ScriptSubcommandBiomeModify());
		SquidUtils.API.getScripting().addCommand("biome", new ScriptCommand(biomesubcommands));

		Map<String, IScriptSubcommand> fishingsubcommands = Maps.newHashMap();
		fishingsubcommands.put("create", new ScriptSubcommandFishingAdd());
		fishingsubcommands.put("remove", new ScriptSubcommandFishingRemove());
		SquidUtils.API.getScripting().addCommand("fishing", new ScriptCommand(fishingsubcommands));

		Map<String, IScriptSubcommand> dungeonmobsubcommands = Maps.newHashMap();
		dungeonmobsubcommands.put("create", new ScriptSubcommandDungeonmobAdd());
		dungeonmobsubcommands.put("remove", new ScriptSubcommandDungeonmobRemove());
		SquidUtils.API.getScripting().addCommand("dungeonmob", new ScriptCommand(dungeonmobsubcommands));

		Map<String, IScriptSubcommand> chestgensubcommands = Maps.newHashMap();
		chestgensubcommands.put("create", new ScriptSubcommandChestgenAdd());
		chestgensubcommands.put("remove", new ScriptSubcommandChestgenRemove());
		SquidUtils.API.getScripting().addCommand("chestgen", new ScriptCommand(chestgensubcommands));

		Map<String, IScriptSubcommand> villagersubcommands = Maps.newHashMap();
		villagersubcommands.put("create", new ScriptSubcommandVillagerAdd());
		villagersubcommands.put("remove", new ScriptSubcommandVillagerRemove());
		SquidUtils.API.getScripting().addCommand("villager", new ScriptCommand(villagersubcommands));

		Map<String, IScriptSubcommand> modsubcommands = Maps.newHashMap();
		modsubcommands.put("Botania", new ScriptSubcommandModsBotania());
		modsubcommands.put("RotaryCraft", new ScriptSubcommandModsRotaryCraft());
		modsubcommands.put("ThermalExpansion", new ScriptSubcommandModsThermalExpansion());
		modsubcommands.put("BloodMagic", new ScriptSubcommandModsBloodMagic());
		modsubcommands.put("ThermalExpansion", new ScriptSubcommandModsRailCraft());
		SquidUtils.API.getScripting().addCommand("mods", new ScriptCommand(modsubcommands));

		Map<String, IScriptSubcommand> achievementsubcommands = Maps.newHashMap();
		achievementsubcommands.put("create", new ScriptSubcommandAchievementCreate());
		achievementsubcommands.put("remove", new ScriptSubcommandAchievementRemove());
		SquidUtils.API.getScripting().addCommand("achievement", new ScriptCommand(achievementsubcommands));

		Map<String, IScriptSubcommand> smeltingsubcommands = Maps.newHashMap();
		smeltingsubcommands.put("create", new ScriptSubcommandSmeltingCreate());
		smeltingsubcommands.put("remove", new ScriptSubcommandSmeltingRemove());
		SquidUtils.API.getScripting().addCommand("smelting", new ScriptCommand(smeltingsubcommands));

		Map<String, IScriptSubcommand> reflectionsubcommands = Maps.newHashMap();
		smeltingsubcommands.put("field", new ScriptSubcommandReflectionField());
		smeltingsubcommands.put("method", new ScriptSubcommandReflectionMethod());
		SquidUtils.API.getScripting().addCommand("reflection", new ScriptCommand(reflectionsubcommands));
	}

	public static class ScriptSubcommandBlockCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (args.get("type").equals("meteor")) {
				MeteorType t = null;
				if (args.get(3).equals("STONE")) {
					t = MeteorType.STONE;
				}
				else if (args.get("meteortype").equals("END")) {
					t = MeteorType.END;
				}
				else if (args.get("meteortype").equals("ICE")) {
					t = MeteorType.ICE;
				}
				else if (args.get("meteortype").equals("OBSIDIAN")) {
					t = MeteorType.OBSIDIAN;
				}
				else if (args.get("meteortype").equals("HELL")) {
					t = MeteorType.HELL;
				}
				new BlockMeteorBase(args.get("name"), t).setCreativeTab(ModCreativeTabs.tab);
			}
			else if (args.get("type").equals("glass")) {
				BlockGlass b = new BlockGlass(Material.glass, false);
				b.setCreativeTab(CreativeTabs.tabBlock).setBlockName(args.get("name"));
				RegistryHelper.registerBlock(b, args.get("name"));
			}
			else if (args.get("type").equals("basic")) {
				BlockBase b = new BlockBase(args.get("name"));
				b.setCreativeTab(CreativeTabs.tabBlock);
			}
		}
	}

	public static class ScriptSubcommandBlockModify implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			Block block = StringParser.parseBlock(args.get("block"));
			String prop = args.get("property");
			String value = args.get("value");
			if (prop.equals("hardness")) {
				block.setHardness(Float.parseFloat(value));
			}
			else if (prop.equals("slipperiness")) {
				block.slipperiness = Float.parseFloat(value);
			}
			else if (prop.equals("resistance")) {
				block.setResistance(Float.parseFloat(value));
			}
			else if (prop.equals("texture")) {
				block.setBlockTextureName(value);
			}
			else if (prop.equals("drops")) {
				Item item = StringParser.parseItem(args.get("item"));
				if (args.get("action").equals("remove")) {
					DropHandler.removeDrop(block, item);
				}
				else if (args.get("action").equals("add")) {
					Chance chance = new Chance(IntUtils.parseInt(args.get("minchance")), IntUtils.parseInt(args.get("maxchance")));
					Drop drop = new Drop(item, IntUtils.parseInt(args.get("minamount")), IntUtils.parseInt(args.get("maxamount")), chance);
					DropHandler.addDrop(block, drop);
				}
			}
			else if (prop.equals("flammability")) {
				Blocks.fire.setFireInfo(StringParser.parseBlock(args.get("block")), Integer.parseInt(args.get("encouragement")), Integer.parseInt(value));
			}
		}
	}

	public static class ScriptSubcommandItemCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String name = args.get("name");
			if (args.get("type").equals("food")) {
				ItemFood food = new ItemFood(IntUtils.parseInt(args.get("bars")), Float.parseFloat(args.get("saturation")), Boolean.parseBoolean(args.get("wolfs")));
				if (Boolean.parseBoolean(args.get("alwaysedible"))) {
					food.setAlwaysEdible();
				}
				if (args.containsKey("potionid")) {
					food.setPotionEffect(IntUtils.parseInt(args.get("potionid")), IntUtils.parseInt(args.get("duration")), IntUtils.parseInt(args.get("amplifier")), Float.parseFloat(args.get("chance")));
				}
				RegistryHelper.registerItem(food, name);
			}
			else if (args.get("type").equals("basic")) {
				new ItemBase(name).setCreativeTab(CreativeTabs.tabMaterials);
			}
		}
	}

	public static class ScriptSubcommandItemModify implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			Item item = StringParser.parseItem(args.get("item"));
			String a = MiscLib.getBlacklister(item);
			if (a != null) {
				SquidUtils.instance().warn(StringUtils.newString(a, " has requested to be blacklisted from modification. ", args.get("item"), " will not be modified."));
				return;
			}
			if (args.get("property").equals("stacksize")) {
				item.setMaxStackSize(IntUtils.parseInt(args.get("value")));
			}
			else if (args.get("property").equals("maxdamage")) {
				item.setMaxDamage(IntUtils.parseInt(args.get("value")));
			}
			else if (args.get("property").equals("tab")) {
				item.setCreativeTab(StringParser.parseCreativeTab(args.get("value")));
			}
		}
	}

	public static class ScriptSubcommandCommandCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String type = args.get("type");
			String name = args.get("name");
			String desc = args.get("description").replace("_", " ");
			if (type.equals("info")) {
				CommandInfo command = new CommandInfo(name, desc, args.get("text").replace("_", " "));
				if (args.containsKey("url")) {
					command.setUrl(args.get("url"));
				}
				SquidAPI.instance().commands.add(command);
			}
			else if (type.equals("web")) {
				CommandWeb command = new CommandWeb(name, desc, args.get("url"));
				SquidAPI.instance().commands.add(command);
			}
			else if (type.equals("basic")) {
				CommandCustom command = new CommandCustom(name, desc, Boolean.parseBoolean(args.get("requiresop")));
				SquidAPI.instance().commands.add(command);
			}
		}
	}

	public static class ScriptSubcommandCommandDisable implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			SquidUtils.COMMON.disableCommand(args.get("name"));
		}
	}

	public static class ScriptSubcommandTabCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Block.blockRegistry.containsKey(args.get("icon"))) {
				new ITab(args.get("name"), Item.getItemFromBlock(StringParser.parseBlock(args.get("icon"))));
			}
			else if (Item.itemRegistry.containsKey(args.get("icon"))) {
				new ITab(args.get("name"), StringParser.parseItem(args.get("icon")));
			}
		}
	}

	public static class ScriptSubcommandRecipeCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String type = args.get("type");
			ItemStack output = StringParser.parseItemStack(args.get("output"));
			String a = MiscLib.getBlacklister(output.getItem());
			if (a != null) {
				SquidUtils.instance().warn(StringUtils.newString(a, " has requested to be blacklisted from modification. ", args.get("item"), " will not be modified."));
				return;
			}
			else if (type.equals("shapeless")) {
				RegistryHelper.addShapelessRecipe(output, new ItemStack[] {StringParser.parseItemStack(args.get("input"))});
			}
			else if (type.equals("furnace")) {
				RegistryHelper.addSmelting(StringParser.parseItem(args.get("input")), output);
			}
		}
	}

	public static class ScriptSubcommandRecipeRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (args.get("type").equals("all") && !ContentRemover.isBlacklistedModLoaded()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
			else {
				ContentRemover.remove(args.get("item"), ContentType.RECIPE);
			}
		}
	}

	public static class ScriptSubcommandBiomeCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			BiomeGenBase biome = new BiomeGenBase(IntUtils.parseInt(args.get("id"))) {};
			biome.biomeName = args.get("name");
			biome.topBlock = StringParser.parseBlock(args.get("topblock"));
			biome.fillerBlock = StringParser.parseBlock(args.get("fillerblock"));
			BiomeManager.addBiome(BiomeType.getType(args.get("type")), new BiomeEntry(biome, IntUtils.parseInt(args.get("weight"))));
		}
	}

	public static class ScriptSubcommandBiomeModify implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String key = args.get("property");
			int id = IntUtils.parseInt(args.get("id"));
			BiomeGenBase biome = BiomeGenBase.getBiome(id);
			String a = MiscLib.getBlacklister(biome);
			if (a != null) {
				SquidUtils.instance().warn(StringUtils.newString(a, " has requested to be blacklisted from modification. ", args.get("item"), " will not be modified."));
				return;
			}
			if (key.equals("topblock")) {
				BiomeGenBase.getBiome(id).topBlock = Block.getBlockFromName(args.get("block"));
			}
			else if (key.equals("fillerblock")) {
				BiomeGenBase.getBiome(id).fillerBlock = Block.getBlockFromName(args.get("block"));
			}
			else if (key.equals("disablerain")) {
				BiomeGenBase.getBiome(id).setDisableRain();
			}
			else if (key.equals("enablesnow")) {
				BiomeGenBase.getBiome(id).setEnableSnow();
			}
			else if (key.equals("height")) {
				BiomeGenBase.getBiome(id).setHeight(new Height(Float.parseFloat(args.get("height")), Float.parseFloat(args.get("variation"))));
			}
			else if (key.equals("addflower")) {
				int weight = IntUtils.parseInt(args.get("weight"));
				int metadata = 0;
				if (args.containsKey("metadata")) {
					metadata = IntUtils.parseInt(args.get("metadata"));
				}
				BiomeGenBase.getBiome(id).addFlower(StringParser.parseBlock(args.get("block")), metadata, weight);
			}
		}
	}

	public static class ScriptSubcommandFishingRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String type = args.get("type");
			String item = args.get("item");
			if (type.equals("fish")) {
				ContentRemover.remove(item, ContentType.FISH);
			}
			else if (type.equals("junk")) {
				ContentRemover.remove(item, ContentType.JUNK);
			}
			else if (type.equals("treasure")) {
				ContentRemover.remove(item, ContentType.TREASURE);
			}
		}
	}

	public static class ScriptSubcommandFishingAdd implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			String type = args.get("type");
			String itemstack = args.get("itemstack");
			int weight = IntUtils.parseInt(args.get("weight"));
			if (type.equals("fish")) {
				FishingHelper.addFish(StringParser.parseItemStack(itemstack), weight);
			}
			else if (type.equals("junk")) {
				FishingHelper.addJunk(StringParser.parseItemStack(itemstack), weight);
			}
			else if (type.equals("treasure")) {
				FishingHelper.addTreasure(StringParser.parseItemStack(itemstack), weight);
			}
		}
	}

	public static class ScriptSubcommandDungeonmobRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			ContentRemover.remove(args.get("mob"), ContentType.DUNGEONMOB);
		}
	}

	public static class ScriptSubcommandDungeonmobAdd implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			DungeonHooks.addDungeonMob(args.get("mob"), IntUtils.parseInt(args.get("rarity")));
		}
	}

	public static class ScriptSubcommandChestgenAdd implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			ChestGenHooks.addItem(args.get("category"), new WeightedRandomChestContent(StringParser.parseItemStack(args.get("item")), IntUtils.parseInt(args.get("minchance")), IntUtils.parseInt(args.get("maxchance")), IntUtils.parseInt(args.get("weight"))));
		}
	}

	public static class ScriptSubcommandChestgenRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			ContentRemover.remove(StringUtils.newString(args.get("category"), ";", args.get("item")), ContentType.CHESTGEN);
		}
	}

	public static class ScriptSubcommandVillagerAdd implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			VillagerRegistry.instance().registerVillagerId(IntUtils.parseInt(args.get("id")));
			VillagerRegistry.instance().registerVillagerSkin(IntUtils.parseInt(args.get("id")), new ResourceLocation("SquidUtils", StringUtils.newString("textures/entity/villager/", args.get("texture"), ".png")));
		}
	}

	public static class ScriptSubcommandVillagerRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			ContentRemover.remove(args.get("id"), ContentType.PROFESSION);
		}
	}

	public static class ScriptSubcommandModsBotania implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Compat.BOTANIA.isEnabled()) {
				String action = args.get("action");
				String type = args.get("type");
				if (action.equals("add")) {
					if (type.equals("manainfusion")) {
						BotaniaCompat.registerManaInfusionRecipe(StringParser.parseItemStack(args.get("output")), StringParser.parseInput(args.get("input")), IntUtils.parseInt(args.get("mana")));
					}
					else if (type.equals("manaalchemy")) {
						BotaniaCompat.registerManaAlchemyRecipe(StringParser.parseItemStack(args.get("output")), StringParser.parseInput(args.get("input")), IntUtils.parseInt(args.get("mana")));
					}
					else if (type.equals("manaconjuration")) {
						BotaniaCompat.registerManaConjurationRecipe(StringParser.parseItemStack(args.get("output")), StringParser.parseInput(args.get("input")), IntUtils.parseInt(args.get("mana")));
					}
					else if (type.equals("petal")) {
						Object[] inputs = new Object[9];
						String[] aa = args.get("input").split(";");
						for (int a = 0; a < aa.length; a++) {
							inputs[a] = StringParser.parseInput(aa[a]);
						}
						BotaniaCompat.registerPetalRecipe(StringParser.parseItemStack(args.get("output")), inputs);
					}
					else if (type.equals("elventrade")) {
						Object[] inputs = new Object[9];
						String[] aa = args.get("input").split(";");
						for (int a = 0; a < aa.length; a++) {
							inputs[a] = StringParser.parseInput(aa[a]);
						}
						BotaniaCompat.registerElvenTradeRecipe(StringParser.parseItemStack(args.get("output")), inputs);
					}
				}
			}
		}
	}

	public static class ScriptSubcommandModsRotaryCraft implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Compat.ROTARYCRAFT.isEnabled()) {
				String action = args.get("action");
				String type = args.get("type");
				if (action.equals("add")) {
					if (type.equals("grinding")) {
						RotaryCraftCompat.addGrindingRecipe(StringParser.parseItemStack(args.get("input")), StringParser.parseItemStack(args.get("output")));
					}
					else if (type.equals("worktable")) {
						if (args.get("subtype").equals("shapeless")) {
							RotaryCraftCompat.addShapelessWorktableRecipe(StringParser.parseItemStack(args.get("output")), StringParser.parseItemStack(args.get("input")));
						}
						else if (args.get("subtype").equals("shaped")) {
							ItemStack[] inputs = new ItemStack[9];
							String[] aa = args.get("input").split(";");
							for (int a = 0; a < aa.length; a++) {
								inputs[a] = StringParser.parseItemStack(aa[a]);
							}
							RotaryCraftCompat.addShapedWorktableRecipe(StringParser.parseItemStack(args.get("output")), inputs);
						}
					}
					else if (type.equals("compactor")) {
						RotaryCraftCompat.addCompactorRecipe(StringParser.parseItemStack(args.get("input")), StringParser.parseItemStack(args.get("output")), IntUtils.parseInt(args.get("pressure")), IntUtils.parseInt(args.get("temperature")));
					}
				}
			}
		}
	}

	public static class ScriptSubcommandModsThermalExpansion implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Compat.THERMALEXPANSION.isEnabled()) {
				String action = args.get("action");
				String type = args.get("type");
				if (action.equals("add")) {
					if (type.equals("pulverizer")) {
						ThermalExpansionCompat.addPulverizerRecipe(StringParser.parseItemStack(args.get("input")), StringParser.parseItemStack(args.get("output")), IntUtils.parseInt(args.get("energy")));
					}
					else if (type.equals("sawmill")) {
						ThermalExpansionCompat.addSawmillRecipe(StringParser.parseItemStack(args.get("input")), StringParser.parseItemStack(args.get("output")), IntUtils.parseInt(args.get("energy")));
					}
				}
			}
		}
	}

	public static class ScriptSubcommandModsRailCraft implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Compat.RAILCRAFT.isEnabled()) {
				String action = args.get("action");
				String type = args.get("type");
				if (action.equals("add")) {
					if (type.equals("blastfurnace")) {
						ItemStack input = StringParser.parseItemStack(args.get("input"));
						ItemStack output = StringParser.parseItemStack(args.get("output"));
						RailCraftCompat.addBlastFurnaceRecipe(input, output, IntUtils.parseInt(args.get("cooktime")));
					}
					else if (type.equals("cokeoven")) {
						ItemStack input = StringParser.parseItemStack(args.get("input"));
						ItemStack output = StringParser.parseItemStack(args.get("output"));
						RailCraftCompat.addCokeOvenRecipe(input, output, new FluidStack(IntUtils.parseInt(args.get("fluidid")), IntUtils.parseInt(args.get("fluidamount"))), IntUtils.parseInt(args.get("cooktime")));
					}
					else if (type.equals("rockcrusher")) {
						ItemStack input = StringParser.parseItemStack(args.get("input"));
						RailCraftCompat.addRockCrusherRecipe(input);
					}
					else if (type.equals("cokeoven")) {
						ItemStack output = StringParser.parseItemStack(args.get("output"));
						ItemStack[] components = new ItemStack[9];
						String[] aa = args.get("input").split(";");
						for (int a = 0; a < aa.length; a++) {
							components[a] = StringParser.parseItemStack(aa[a]);
						}
						RailCraftCompat.addRollingMachineRecipe(output, components);
					}
				}
			}
		}
	}

	public static class ScriptSubcommandModsBloodMagic implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (Compat.BLOODMAGIC.isEnabled()) {
				String action = args.get("action");
				String type = args.get("type");
				if (action.equals("add")) {
					if (type.equals("bloodaltar")) {
						ItemStack input = StringParser.parseItemStack(args.get("input"));
						ItemStack output = StringParser.parseItemStack(args.get("output"));
						int minTier = IntUtils.parseInt(args.get("mintier"));
						int liquidRequired = IntUtils.parseInt(args.get("liquidrequired"));
						int consumptionRate = IntUtils.parseInt(args.get("consumptionrate"));
						int drainRate = IntUtils.parseInt(args.get("drainrate"));
						boolean canBeFilled = Boolean.parseBoolean(args.get("canbefilled"));
						BloodMagicCompat.addBloodAltarRecipe(input, output, minTier, liquidRequired, consumptionRate, drainRate, canBeFilled);
					}
				}
			}
		}
	}

	public static class ScriptSubcommandAchievementCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			if (args.containsKey("parent")) {
				AchievementHelper.addAchievement(args.get("name"), Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), StringParser.parseItem(args.get("icon")), AchievementHelper.getAchievement(args.get("parent")));
			}
			else {
				AchievementHelper.addAchievement(args.get("name"), Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), StringParser.parseItem(args.get("icon")));
			}
		}
	}

	public static class ScriptSubcommandAchievementRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			Achievement target = AchievementHelper.getAchievement(args.get("name"));
			for (int a = 0; a < AchievementList.achievementList.size(); a++) {
				Achievement b = (Achievement) AchievementList.achievementList.get(a);
				if (b == target) {
					AchievementList.achievementList.remove(a);
				}
				else if (b.parentAchievement == target) {
					ReflectionHelper.in(b).finalField("parentAchievement", "field_75992_c").set(AchievementHelper.getAchievement(args.get("replacement")));
				}
			}
		}
	}

	public static class ScriptSubcommandSmeltingCreate implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			RegistryHelper.addSmelting(StringParser.parseItem(args.get("input")), StringParser.parseItemStack(args.get("output")));
		}
	}

	public static class ScriptSubcommandSmeltingRemove implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			ContentRemover.remove(args.get("output"), ContentType.SMELTING);
		}
	}

	public static class ScriptSubcommandReflectionMethod implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			try {
				Class<?> a = Class.forName(args.get("class"));
				Method b = a.getMethod(args.get("name"));
				b.setAccessible(true);
				b.invoke(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static class ScriptSubcommandReflectionField implements IScriptSubcommand {

		@Override
		public void run(Map<String, String> args) {
			try {
				Class<?> a = Class.forName(args.get("class"));
				Field b = a.getField(args.get("name"));
				b.setAccessible(true);
				if (args.containsKey("final") && args.get("final").equals("true")) {
					Field c = Field.class.getDeclaredField("modifiers");
					c.setAccessible(true);
					c.setInt(b, b.getModifiers() & ~Modifier.FINAL);
				}
				b.set(null, Double.parseDouble(args.get("value")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}