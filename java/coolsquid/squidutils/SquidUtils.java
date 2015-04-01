/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils;

import java.io.File;
import java.util.HashSet;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import coolsquid.squidapi.Disableable;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.SquidAPIMod;
import coolsquid.squidapi.config.SquidAPIConfig;
import coolsquid.squidapi.exception.InvalidConfigValueException;
import coolsquid.squidapi.helpers.APIHelper;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.registry.DamageSourceRegistry;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.ContentRemover.ContentType;
import coolsquid.squidapi.util.EmptyEnchantment;
import coolsquid.squidapi.util.EmptyPotion;
import coolsquid.squidapi.util.IterableMap;
import coolsquid.squidapi.util.StringParser;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.api.ScriptingAPI;
import coolsquid.squidutils.command.CommandSquidUtils;
import coolsquid.squidutils.compat.AppleCoreCompat;
import coolsquid.squidutils.config.BlockConfigHandler;
import coolsquid.squidutils.config.ConfigHandler;
import coolsquid.squidutils.config.ItemConfigHandler;
import coolsquid.squidutils.creativetab.ModCreativeTabs;
import coolsquid.squidutils.handlers.AchievementHandler;
import coolsquid.squidutils.handlers.AnvilHandler;
import coolsquid.squidutils.handlers.BlockBoxHandler;
import coolsquid.squidutils.handlers.BonemealHandler;
import coolsquid.squidutils.handlers.BottleHandler;
import coolsquid.squidutils.handlers.BreakSpeedHandler;
import coolsquid.squidutils.handlers.CommandHandler;
import coolsquid.squidutils.handlers.CraftingHandler;
import coolsquid.squidutils.handlers.DamageHandler;
import coolsquid.squidutils.handlers.DebugHandler;
import coolsquid.squidutils.handlers.DifficultyHandler;
import coolsquid.squidutils.handlers.DropHandler;
import coolsquid.squidutils.handlers.EntityJoinHandler;
import coolsquid.squidutils.handlers.EventLogger;
import coolsquid.squidutils.handlers.ExplosionHandler;
import coolsquid.squidutils.handlers.GuiHandler;
import coolsquid.squidutils.handlers.HealingHandler;
import coolsquid.squidutils.handlers.InteractionHandler;
import coolsquid.squidutils.handlers.ItemBanHandler;
import coolsquid.squidutils.handlers.LivingUpdateHandler;
import coolsquid.squidutils.handlers.MinecartCollisionHandler;
import coolsquid.squidutils.handlers.RegistrySearcher;
import coolsquid.squidutils.handlers.RenderDistanceHandler;
import coolsquid.squidutils.handlers.ServerChatHandler;
import coolsquid.squidutils.handlers.SmeltingHandler;
import coolsquid.squidutils.handlers.SpeedHandler;
import coolsquid.squidutils.handlers.StackSizeHandler;
import coolsquid.squidutils.handlers.TNTHandler;
import coolsquid.squidutils.handlers.TeleportationHandler;
import coolsquid.squidutils.handlers.ToolHandler;
import coolsquid.squidutils.handlers.ToolTipHandler;
import coolsquid.squidutils.handlers.TossHandler;
import coolsquid.squidutils.handlers.VillagerHandler;
import coolsquid.squidutils.handlers.WitherHandler;
import coolsquid.squidutils.helpers.PermissionHelper;
import coolsquid.squidutils.scripting.ScriptHandler;
import coolsquid.squidutils.scripting.components.Components;
import coolsquid.squidutils.util.CrashReportInterceptor.CrashMessage;
import coolsquid.squidutils.util.CrashReportInterceptor.Modified;
import coolsquid.squidutils.util.ModInfo;
import coolsquid.squidutils.util.ModLister;
import coolsquid.squidutils.util.PackIntegrityChecker;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
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

	@Instance("SquidAPI")
	private static SquidAPI squidapi;
	@Instance
	private static SquidUtils instance;
	private final ModContainer api = APIHelper.INSTANCE.getAPI("SquidUtils|ScriptingAPI");

	public SquidUtils() {
		super("Customization to the max!");
	}

	public static SquidUtils instance() {
		return instance;
	}

	public ModContainer api() {
		return this.api;
	}

	public final Map<String, Object> handlers = Maps.newHashMap();
	public final Map<String, Object> handlers2 = Maps.newHashMap();

	public void registerHandler(Object object) {
		String name = object.getClass().getSimpleName();
		this.info("Registering handler ", name, ".");
		MinecraftForge.EVENT_BUS.register(object);
		this.handlers.put(name, object);
	}

	public void registerHandler2(Object object) {
		String name = object.getClass().getSimpleName();
		this.info("Registering handler ", name, ".");
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
		this.info("Preinitializing.");
		this.info("Version id: ", this.hash());

		this.setDisableable();

		new File("./config/SquidUtils").mkdirs();
		ConfigHandler.INSTANCE.preInit(new File("./config/SquidUtils/SquidUtils.cfg"));

		if (ConfigHandler.INSTANCE.modList.length > 0) {
			PackIntegrityChecker.INSTANCE.check();
			FMLCommonHandler.instance().registerCrashCallable(new Modified());
		}
		if (ConfigHandler.INSTANCE.clearRecipes == 1) {
			CraftingManager.getInstance().getRecipeList().clear();
		}

		this.info("Preinitialization finished.");
	}
	
	/**
	 * Init. Activates modules.
	 * @param event
	 */
	
	@EventHandler
	private void init(FMLInitializationEvent event) {
		this.info("Initializing.");

		this.suggestMod("OpenEye", "Automatic crash reporting!", "http://bit.ly/1wE0yfe");
		this.suggestMod("YAMPST", "yampst", "Statistics tool for modpacks!", "http://bit.ly/1HatUVT");
		this.suggestMod("MobProperties", "It allows the user to configure almost any aspect of any mob!", "http://bit.ly/1EcMZHr");
		this.suggestMod("JSONAbles", "jsonables", "Add custom TiCon tools!", "http://bit.ly/1CFa7Ai");
		this.suggestMod("Quadrum", "Add custom blocks and items!", "http://bit.ly/1EB3U62");
		this.suggestMod("MineTweaker3", "Add smelting, crafting, fuels, and so much more!", "http://bit.ly/1ubqwop");
		if (Loader.isModLoaded("MineTweaker3")) {
			this.suggestMod("MTRM", "Adds an ingame GUI for MineTweaker!", "http://bit.ly/1xGYYYr");
			this.suggestMod("ModTweaker", "Mod support for MineTweaker!", "http://bit.ly/1lIxEYX");
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

		ScriptHandler.INSTANCE.init();

		if (Utils.isClient()) {
			this.registerHandler(new DifficultyHandler());
			if (!ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("FALSE")) {
				DifficultyHandler.difficulty = ConfigHandler.INSTANCE.forceDifficulty;
			}
		}
		if (!ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("FALSE") && !ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("EASY") && !ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("NORMAL") && !ConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("forceDifficulty");
		}
		if (ConfigHandler.INSTANCE.noTNT) {
			this.registerHandler(new TNTHandler());
		}
		if (ConfigHandler.INSTANCE.noAchievements || ScriptHandler.INSTANCE.onAchievement) {
			if (ConfigHandler.INSTANCE.keepTTCoreBug) {
				this.registerHandler(new AchievementHandler() {
					@Override
					@SubscribeEvent
					public final void onAchievement(AchievementEvent event) {
						if (ConfigHandler.INSTANCE.noAchievements) event.setCanceled(true);
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
		if (ConfigHandler.INSTANCE.noWitherBoss) {
			this.registerHandler(new WitherHandler());
		}
		if (ConfigHandler.INSTANCE.chainRecipes) {
			ModRecipes.chain();
		}
		if (ConfigHandler.INSTANCE.noDebug && Utils.isClient()) {
			this.registerHandler(new DebugHandler());
		}
		if (ConfigHandler.INSTANCE.maxRenderDistance != 16 && Utils.isClient()) {
			this.registerHandler(new RenderDistanceHandler());
		}
		if (ConfigHandler.INSTANCE.villagerProtection) {
			this.registerHandler(new VillagerHandler());
		}
		if (ConfigHandler.INSTANCE.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ConfigHandler.INSTANCE.logStuff) {
			this.registerHandler(new EventLogger());
		}
		if (ConfigHandler.INSTANCE.disableAnvil) {
			this.registerHandler(new AnvilHandler());
		}
		if (ScriptHandler.INSTANCE.onCommand) {
			this.registerHandler(new CommandHandler());
		}
		if (ConfigHandler.INSTANCE.disableTeleportation || ScriptHandler.INSTANCE.onTeleport) {
			this.registerHandler(new TeleportationHandler());
		}
		if (ConfigHandler.INSTANCE.disableBonemeal) {
			this.registerHandler(new BonemealHandler());
		}
		if (ConfigHandler.INSTANCE.disableHoes) {
			this.registerHandler(new ToolHandler());
		}
		if (ConfigHandler.INSTANCE.disableBottleFluidInteraction) {
			this.registerHandler(new BottleHandler());
		}
		if (ConfigHandler.INSTANCE.generateModList != 0) {
			ModLister.INSTANCE.init();
		}
		if (ConfigHandler.INSTANCE.walkSpeed != 0.1F || ConfigHandler.INSTANCE.flySpeed != 0.05F) {
			this.registerHandler(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.INSTANCE.onCraft) {
			this.registerHandler2(new CraftingHandler());
		}
		if (ScriptHandler.INSTANCE.onSmelt) {
			this.registerHandler2(new SmeltingHandler());
		}
		if (ScriptHandler.INSTANCE.onHurt) {
			this.registerHandler(new DamageHandler());
		}
		if (ScriptHandler.INSTANCE.onHeal) {
			this.registerHandler(new HealingHandler());
		}
		if (ScriptHandler.INSTANCE.onToss) {
			this.registerHandler(new TossHandler());
		}
		if (ConfigHandler.INSTANCE.explosionSizeMultiplier != 1) {
			this.registerHandler(new ExplosionHandler());
		}
		if (ScriptHandler.INSTANCE.onInteraction) {
			this.registerHandler(new InteractionHandler());
		}
		if (ScriptHandler.INSTANCE.onChat) {
			this.registerHandler(new ServerChatHandler());
		}
		if (ScriptHandler.INSTANCE.permissions) {
			PermissionHelper.INSTANCE.init();
			this.registerHandler(PermissionHelper.INSTANCE);
		}
		if (ConfigHandler.INSTANCE.worldSize > 0) {
			this.registerHandler(new LivingUpdateHandler());
		}
		if (ConfigHandler.INSTANCE.explodeTNTMinecartsOnCollide) {
			this.registerHandler(new MinecartCollisionHandler());
		}
		if (ConfigHandler.INSTANCE.removeBlockHighlight) {
			MinecraftForge.EVENT_BUS.register(BlockBoxHandler.INSTANCE);
		}
		MinecraftForge.EVENT_BUS.register(new GuiHandler());

		if (Utils.isClient()) {
			SquidAPI.instance().registerCommands(new CommandSquidUtils());
		}

		Utils.runVersionCheckerCompat("226025");

		this.info("Initialization finished.");
	}
	
	/**
	 * My postinit. Used for compatibility and universal changes.
	 * @param event
	 */
	
	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		this.info("Postinitializing.");

		BlockConfigHandler.INSTANCE.init(new File("./config/SquidUtils/BlockProperties.cfg"));
		ItemConfigHandler.INSTANCE.init(new File("./config/SquidUtils/ItemProperties.cfg"));

		if (!ToolTipHandler.INSTANCE.getTooltips().isEmpty()) {
			MinecraftForge.EVENT_BUS.register(ToolTipHandler.INSTANCE);
		}

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
						this.warn(Utils.newString(name.split(":")[0], " has requested to be blacklisted from content removal. ", name, " will not be removed."));
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
					if (c.equals("empty")) {
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
					if (c.equals("empty")) {
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

		if (ConfigHandler.INSTANCE.flammabilityMultiplier != 1) {
			for (Object a: Block.blockRegistry) {
				Block b = (Block) a;
				Blocks.fire.setFireInfo(b, Blocks.fire.getEncouragement(b), Blocks.fire.getFlammability(b) * ConfigHandler.INSTANCE.flammabilityMultiplier);
			}
		}

		if (!ItemBanHandler.bannedItems.isEmpty()) {
			this.registerHandler(new ItemBanHandler());
		}
		if (ScriptHandler.INSTANCE.onEntityJoin || !EntityJoinHandler.disable.isEmpty()) {
			this.registerHandler(new EntityJoinHandler());
		}

		RegistrySearcher.start();

		if (ConfigHandler.INSTANCE.potionStacks > 1 || ConfigHandler.INSTANCE.pearlStack > 1) {
			StackSizeHandler.some(ConfigHandler.INSTANCE.potionStacks, ConfigHandler.INSTANCE.pearlStack);
		}

		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			this.registerHandler(new DropHandler());
		}

		this.info("Postinitialization finished.");
	}
	
	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (ConfigHandler.INSTANCE.clearRecipes == 2) {
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
		this.info("SquidUtils has been disabled.");
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
		this.info("SquidUtils has been enabled.");
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
		if (ConfigHandler.INSTANCE.removeAllCommands) {
			ServerHelper.getCommands().clear();
		}
		else {
			for (String command: this.commandsToDisable) {
				ServerHelper.removeCommand(command);
			}
		}
	}
}