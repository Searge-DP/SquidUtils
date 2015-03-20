/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package com.github.coolsquid.squidutils;

import java.io.File;
import java.util.HashSet;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;

import com.github.coolsquid.squidapi.Disableable;
import com.github.coolsquid.squidapi.SquidAPI;
import com.github.coolsquid.squidapi.SquidAPIMod;
import com.github.coolsquid.squidapi.command.CommandDisable;
import com.github.coolsquid.squidapi.config.SquidAPIConfig;
import com.github.coolsquid.squidapi.exception.InvalidConfigValueException;
import com.github.coolsquid.squidapi.helpers.server.ServerHelper;
import com.github.coolsquid.squidapi.reflection.ReflectionHelper;
import com.github.coolsquid.squidapi.registry.DamageSourceRegistry;
import com.github.coolsquid.squidapi.util.ContentRemover;
import com.github.coolsquid.squidapi.util.ContentRemover.ContentType;
import com.github.coolsquid.squidapi.util.EmptyEnchantment;
import com.github.coolsquid.squidapi.util.EmptyPotion;
import com.github.coolsquid.squidapi.util.IterableMap;
import com.github.coolsquid.squidapi.util.StringParser;
import com.github.coolsquid.squidapi.util.Utils;
import com.github.coolsquid.squidutils.api.ScriptingAPI;
import com.github.coolsquid.squidutils.command.CommandSquidUtils;
import com.github.coolsquid.squidutils.compat.AppleCoreCompat;
import com.github.coolsquid.squidutils.config.ConfigHandler;
import com.github.coolsquid.squidutils.creativetab.ModCreativeTabs;
import com.github.coolsquid.squidutils.handlers.AchievementHandler;
import com.github.coolsquid.squidutils.handlers.AnvilHandler;
import com.github.coolsquid.squidutils.handlers.BonemealHandler;
import com.github.coolsquid.squidutils.handlers.BottleHandler;
import com.github.coolsquid.squidutils.handlers.BreakSpeedHandler;
import com.github.coolsquid.squidutils.handlers.CommandHandler;
import com.github.coolsquid.squidutils.handlers.CraftingHandler;
import com.github.coolsquid.squidutils.handlers.DamageHandler;
import com.github.coolsquid.squidutils.handlers.DebugHandler;
import com.github.coolsquid.squidutils.handlers.DifficultyHandler;
import com.github.coolsquid.squidutils.handlers.DropHandler;
import com.github.coolsquid.squidutils.handlers.EntityJoinHandler;
import com.github.coolsquid.squidutils.handlers.EventLogger;
import com.github.coolsquid.squidutils.handlers.ExplosionHandler;
import com.github.coolsquid.squidutils.handlers.HealingHandler;
import com.github.coolsquid.squidutils.handlers.InteractionHandler;
import com.github.coolsquid.squidutils.handlers.ItemBanHandler;
import com.github.coolsquid.squidutils.handlers.LivingUpdateHandler;
import com.github.coolsquid.squidutils.handlers.MinecartCollisionHandler;
import com.github.coolsquid.squidutils.handlers.RegistrySearcher;
import com.github.coolsquid.squidutils.handlers.RenderDistanceHandler;
import com.github.coolsquid.squidutils.handlers.ServerChatHandler;
import com.github.coolsquid.squidutils.handlers.SmeltingHandler;
import com.github.coolsquid.squidutils.handlers.SpeedHandler;
import com.github.coolsquid.squidutils.handlers.StackSizeHandler;
import com.github.coolsquid.squidutils.handlers.TNTHandler;
import com.github.coolsquid.squidutils.handlers.TeleportationHandler;
import com.github.coolsquid.squidutils.handlers.ToolHandler;
import com.github.coolsquid.squidutils.handlers.TossHandler;
import com.github.coolsquid.squidutils.handlers.VillagerHandler;
import com.github.coolsquid.squidutils.handlers.WitherHandler;
import com.github.coolsquid.squidutils.helpers.LogHelper;
import com.github.coolsquid.squidutils.helpers.PermissionHelper;
import com.github.coolsquid.squidutils.scripting.ScriptHandler;
import com.github.coolsquid.squidutils.scripting.components.Components;
import com.github.coolsquid.squidutils.util.CrashReportInterceptor.CrashMessage;
import com.github.coolsquid.squidutils.util.CrashReportInterceptor.Modified;
import com.github.coolsquid.squidutils.util.ModInfo;
import com.github.coolsquid.squidutils.util.ModLister;
import com.github.coolsquid.squidutils.util.PackIntegrityChecker;
import com.github.coolsquid.squidutils.util.script.EventEffectHelper;
import com.github.coolsquid.squidutils.util.script.EventInfo;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.API;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod implements Disableable {
	
	@Instance
	private static SquidUtils instance;
	@Deprecated
	private API api;
	
	public SquidUtils() {
		super("Customization to the max!");
	}
	
	public static SquidUtils instance() {
		return instance;
	}
	
	public API getAPI() {
		if (this.api == null) {
			this.api = ReflectionHelper.in("com.github.coolsquid.squidutils.api").getAnnotation(API.class);
		}
		return this.api;
	}
	
	public final Map<String, Object> handlers = Maps.newHashMap();
	public final Map<String, Object> handlers2 = Maps.newHashMap();

	public void registerHandler(Object object) {
		String name = object.getClass().getSimpleName();
		LogHelper.info("Registering handler ", name, ".");
		MinecraftForge.EVENT_BUS.register(object);
		this.handlers.put(name, object);
	}
	
	public void registerHandler2(Object object) {
		String name = object.getClass().getSimpleName();
		LogHelper.info("Registering handler ", name, ".");
		FMLCommonHandler.instance().bus().register(object);
		this.handlers2.put(name, object);
	}
	
	public boolean isDisabled;
	
	private final SquidAPIConfig mobs = new SquidAPIConfig(new File("./config/SquidUtils/Mobs.cfg"));
	private final SquidAPIConfig items = new SquidAPIConfig(new File("./config/SquidUtils/Items.cfg"));
	private final SquidAPIConfig potions = new SquidAPIConfig(new File("./config/SquidUtils/Potions.cfg"));
	private final SquidAPIConfig enchantments = new SquidAPIConfig(new File("./config/SquidUtils/Enchantments.cfg"));
	private final SquidAPIConfig layeredHardness = new SquidAPIConfig(new File("./config/SquidUtils/LayeredHardness.cfg"));
	private final SquidAPIConfig crashMessages = new SquidAPIConfig(new File("./config/SquidUtils/CrashMessages.cfg"));
	private final SquidAPIConfig worldTypes = new SquidAPIConfig(new File("./config/SquidUtils/WorldTypes.cfg"));
	private final SquidAPIConfig damageSources = new SquidAPIConfig(new File("./config/SquidUtils/DamageSources.cfg"));
	
	/**
	 * Preinit. Loads the config, clears Vanilla recipes (if toggled).
	 * @param event
	 */
	
	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Preinitializing.");

		CommandDisable.disableables.put("SquidUtils", this);

		new File("./config/SquidUtils").mkdirs();
		ConfigHandler.preInit(new File("./config/SquidUtils/SquidUtils.cfg"));

		if (ConfigHandler.modList.length != 0) {
			PackIntegrityChecker.check();
			FMLCommonHandler.instance().registerCrashCallable(new Modified());
		}
		if (ConfigHandler.clearRecipes == 1) {
			CraftingManager.getInstance().getRecipeList().clear();
		}

		LogHelper.info("Preinitialization finished.");
	}
	
	/**
	 * Init. Activates modules.
	 * @param event
	 */
	
	@EventHandler
	private void init(FMLInitializationEvent event) {
		LogHelper.info("Initializing.");

		this.suggestMod("OpenEye", "Automatic crash reporting!", "http://bit.ly/1wE0yfe");
		this.suggestMod("YAMPST", "Statistics tool for modpacks!", "http://bit.ly/1HatUVT");
		this.suggestMod("MobProperties", "It allows the user to configure almost any aspect of any mob!", "http://bit.ly/1EcMZHr");
		this.suggestMod("JSONAbles", "Add custom TiCon tools!", "http://bit.ly/1CFa7Ai");
		this.suggestMod("Quadrum", "Add custom blocks and items!", "http://bit.ly/1EB3U62");
		this.suggestMod("Minetweaker3", "Add smelting, crafting, fuels, and so much more!", "http://bit.ly/1ubqwop");
		if (Loader.isModLoaded("Minetweaker3")) {
			this.suggestMod("MTRM", "Adds an ingame GUI for Minetweaker!", "http://bit.ly/1xGYYYr");
			this.suggestMod("ModTweaker2", "", "http://bit.ly/1lIxEYX");
		}
		this.suggestMod("Lockdown", "Allows the user to choose a map which will always be generated instead of a normal world.", "http://bit.ly/1zXDc7L");

		Components.init();

		ScriptingAPI.addTrigger("achievement", new AchievementHandler());
		ScriptingAPI.addTrigger("command", new CommandHandler());
		ScriptingAPI.addTrigger("teleport", new TeleportationHandler());
		ScriptingAPI.addTrigger("craft", new CraftingHandler());
		ScriptingAPI.addTrigger("smelt", new SmeltingHandler());
		ScriptingAPI.addTrigger("hurt", new DamageHandler());
		ScriptingAPI.addTrigger("heal", new HealingHandler());
		ScriptingAPI.addTrigger("toss", new TossHandler());
		ScriptingAPI.addTrigger("entityjoin", new EntityJoinHandler());
		ScriptingAPI.addTrigger("explosion", new ExplosionHandler());
		ScriptingAPI.addTrigger("interact", new InteractionHandler());
		ScriptingAPI.addTrigger("chat", new ServerChatHandler());

		ScriptHandler.init();

		if (Utils.isClient()) {
			this.registerHandler(new DifficultyHandler());
			if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE")) {
				DifficultyHandler.difficulty = ConfigHandler.forceDifficulty;
			}
		}
		if (!ConfigHandler.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("forceDifficulty");
		}
		if (ConfigHandler.noTNT) {
			this.registerHandler(new TNTHandler());
		}
		if (ConfigHandler.noAchievements || ScriptHandler.onAchievement) {
			if (ConfigHandler.keepTTCoreBug) {
				this.registerHandler(new AchievementHandler() {
					@Override
					@SubscribeEvent
					public final void onAchievement(AchievementEvent event) {
						if (ConfigHandler.noAchievements) event.setCanceled(true);
						for (EventInfo a: info) {
							EventEffectHelper.performEffects(a, event.entityLiving);
							if (a.values.containsKey("cancel")) event.setCanceled(true);
						}
					}
				});
			}
			else {
				this.registerHandler(new AchievementHandler());
			}
		}
		if (ConfigHandler.noWitherBoss) {
			this.registerHandler(new WitherHandler());
		}
		if (ConfigHandler.chainRecipes) {
			ModRecipes.chain();
		}
		if (ConfigHandler.noDebug && Utils.isClient()) {
			this.registerHandler(new DebugHandler());
		}
		if (ConfigHandler.maxRenderDistance != 16 && Utils.isClient()) {
			this.registerHandler(new RenderDistanceHandler());
		}
		if (ConfigHandler.villagerProtection) {
			this.registerHandler(new VillagerHandler());
		}
		if (ConfigHandler.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ConfigHandler.logStuff) {
			this.registerHandler(new EventLogger());
		}
		if (ConfigHandler.disableAnvil) {
			this.registerHandler(new AnvilHandler());
		}
		if (ScriptHandler.onCommand) {
			this.registerHandler(new CommandHandler());
		}
		if (ConfigHandler.disableTeleportation || ScriptHandler.onTeleport) {
			this.registerHandler(new TeleportationHandler());
		}
		if (ConfigHandler.disableBonemeal) {
			this.registerHandler(new BonemealHandler());
		}
		if (ConfigHandler.disableHoes) {
			this.registerHandler(new ToolHandler());
		}
		if (ConfigHandler.disableBottleFluidInteraction) {
			this.registerHandler(new BottleHandler());
		}
		if (ConfigHandler.generateModList != 0) {
			ModLister.init();
		}
		if (ConfigHandler.walkSpeed != 0.1F || ConfigHandler.flySpeed != 0.05F) {
			this.registerHandler(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.onCraft) {
			this.registerHandler2(new CraftingHandler());
		}
		if (ScriptHandler.onSmelt) {
			this.registerHandler2(new SmeltingHandler());
		}
		if (ScriptHandler.onHurt) {
			this.registerHandler(new DamageHandler());
		}
		if (ScriptHandler.onHeal) {
			this.registerHandler(new HealingHandler());
		}
		if (ScriptHandler.onToss) {
			this.registerHandler(new TossHandler());
		}
		if (ConfigHandler.explosionSizeMultiplier != 1) {
			this.registerHandler(new ExplosionHandler());
		}
		if (ScriptHandler.onInteraction) {
			this.registerHandler(new InteractionHandler());
		}
		if (ScriptHandler.onChat) {
			this.registerHandler(new ServerChatHandler());
		}
		if (ScriptHandler.permissions) {
			PermissionHelper.init();
			this.registerHandler(new PermissionHelper());
		}
		if (ConfigHandler.worldSize > 0) {
			this.registerHandler(new LivingUpdateHandler());
		}
		if (ConfigHandler.explodeTNTMinecartsOnCollide) {
			this.registerHandler(new MinecartCollisionHandler());
		}
		
		if (Utils.isClient()) {
			SquidAPI.registerCommands(new CommandSquidUtils());
		}
		
		Utils.runVersionCheckerCompat("226025");
		
		LogHelper.info("Initialization finished.");
	}
	
	/**
	 * My postinit. Used for compatibility and universal changes.
	 * @param event
	 */
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Postinitializing.");
		
		this.mobs.addHeader("//Mobs to disable:");
		for (Object name: EntityList.stringToClassMapping.keySet()) {
			if (this.mobs.get((String) name, false)) {
				@SuppressWarnings("unchecked")
				Class<? extends Entity> entityclass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(name);
				EntityJoinHandler.disable.add(entityclass);
			}
		}
		
		this.items.addHeader("//Items to disable");
		for (Object item: Item.itemRegistry) {
			if (item instanceof Item) {
				String name = Item.itemRegistry.getNameForObject(item);
				if (this.items.get(name, false)) {
					if (ContentRemover.getBlacklist().isBlacklisted(item)) {
						LogHelper.warn(Utils.newString(name.split(":")[0], " has requested to be blacklisted from content removal. ", name, " will not be removed."));
						return;
					}
					ContentRemover.remove(name, ContentType.RECIPE);
					ContentRemover.remove(name, ContentType.SMELTING);
					ItemBanHandler.bannedItems.add(name);
				}
			}
		}
		
		this.potions.addHeader("//Potions to disable");
		for (int a = 0; a < Potion.potionTypes.length; a++) {
			Potion b = Potion.potionTypes[a];
			if (b != null) {
				String c = this.potions.get(b.getName(), b.getName());
				if (!c.equals(b.getName())) {
					if (c.isEmpty()) {
						EmptyPotion.replacePotion(a);
					}
					else {
						Potion.potionTypes[a] = StringParser.parsePotion(c);
					}
				}
			}
		}
		
		this.enchantments.addHeader("//Enchantments to replace");
		for (int a = 0; a < Enchantment.enchantmentsList.length; a++) {
			Enchantment b = Enchantment.enchantmentsList[a];
			if (b != null) {
				String c = this.enchantments.get(b.getName(), b.getName());
				if (!c.equals(b.getName())) {
					if (c.isEmpty()) {
						Enchantment.enchantmentsList[a] = null;
						new EmptyEnchantment(a);
					}
					else {
						Enchantment.enchantmentsList[a] = StringParser.parseEnchantment(c);
					}
				}
			}
		}
		
		this.layeredHardness.addHeader("//Layered hardness");
		for (int a = 1; a < 27; a++) {
			BreakSpeedHandler.layers.put(a * 10, this.layeredHardness.get(Utils.newString("layer", a), 1F));
		}
		
		for (Float a: BreakSpeedHandler.layers.values()) {
			if (a != 1F) {
				this.registerHandler(new BreakSpeedHandler());
				break;
			}
		}

		this.crashMessages.addHeader("//Crash messages");
		IterableMap<String, Object> crashMessages = this.crashMessages.getEntries();
		for (String label: crashMessages) {
			FMLCommonHandler.instance().registerCrashCallable(new CrashMessage(label, crashMessages.get(label).toString()));
		}

		this.worldTypes.addHeader("//Worldtypes to replace");
		for (int a = 0; a < WorldType.worldTypes.length; a++) {
			WorldType worldtype = WorldType.worldTypes[a];
			if (worldtype != null) {
				WorldType replacement = StringParser.parseWorldType(this.worldTypes.get(worldtype.getWorldTypeName(), worldtype.getWorldTypeName()));
				if (worldtype != replacement) {
					WorldType.worldTypes[a] = replacement;
				}
			}
		}
		
		this.damageSources.addHeader("//Damagesources to disable");
		for (DamageSource dmg: DamageSourceRegistry.instance()) {
			if (this.damageSources.get(dmg.damageType, false)) {
				DamageHandler.bannedDamageSources.add(dmg);
			}
		}

		if (!ItemBanHandler.bannedItems.isEmpty()) {
			this.registerHandler(new ItemBanHandler());
		}
		if (ScriptHandler.onEntityJoin || !EntityJoinHandler.disable.isEmpty()) {
			this.registerHandler(new EntityJoinHandler());
		}
		
		RegistrySearcher.start();
		
		if (ConfigHandler.potionStacks > 1 || ConfigHandler.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.potionStacks, ConfigHandler.pearlStack);
		}
		
		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			this.registerHandler(new DropHandler());
		}
		
		LogHelper.info("Postinitialization finished.");
	}
	
	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (ConfigHandler.clearRecipes == 2) {
			if (!ContentRemover.isBlacklistedModLoaded()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
		}
	}
	
	@Override
	public void disable() {
		for (Object object: this.handlers.values()) {
			MinecraftForge.EVENT_BUS.unregister(object);
		}
		for (Object object: this.handlers2.values()) {
			FMLCommonHandler.instance().bus().unregister(object);
		}
		this.isDisabled = true;
		this.getMod().setEnabledState(false);
		LogHelper.info("SquidUtils has been disabled.");
	}

	@Override
	public void enable() {
		for (Object object: this.handlers.values()) {
			MinecraftForge.EVENT_BUS.register(object);
		}
		for (Object object: this.handlers2.values()) {
			FMLCommonHandler.instance().bus().register(object);
		}
		this.isDisabled = false;
		this.getMod().setEnabledState(true);
		LogHelper.info("SquidUtils has been enabled.");
	}
	
	@EventHandler
	public void onIMC(IMCEvent event) {
		for (IMCMessage msg: event.getMessages()) {
			if (msg.getMessageType() == String.class && msg.key.equals("script")) {
				String[] args = msg.getStringValue().split(" ");
				Builder<String, String> builder = ImmutableMap.builder();
				for (int b = 2; b < args.length; b++) {
					String[] aa = args[b].split("=");
					if (aa.length == 1) {
						builder.put(aa[0], "");
					}
					else if (aa.length == 2) {
						builder.put(aa[0], aa[1]);
					}
				}
				ScriptingAPI.getCommands().get(args[0]).getSubcommands().get(args[1]).run(builder.build());
			}
		}
	}
	
	public final HashSet<String> commandsToDisable = new HashSet<String>();
	
	@EventHandler
	public void onServerStarted(FMLServerStartedEvent event) {
		if (ConfigHandler.removeAllCommands) {
			ServerHelper.getCommands().clear();
		}
		else {
			for (String command: this.commandsToDisable) {
				ServerHelper.removeCommand(command);
			}
		}
	}
}