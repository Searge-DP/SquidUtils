/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils;

import java.io.File;
import java.util.HashSet;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;

import org.lwjgl.opengl.Display;

import com.google.common.collect.Maps;

import coolsquid.squidapi.Disableable;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.SquidAPIMod;
import coolsquid.squidapi.compat.Compat;
import coolsquid.squidapi.config.ConfigurationManager;
import coolsquid.squidapi.exception.InvalidConfigValueException;
import coolsquid.squidapi.helpers.APIHelper;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.api.ScriptingAPI;
import coolsquid.squidutils.asm.Hooks;
import coolsquid.squidutils.command.CommandSquidUtils;
import coolsquid.squidutils.compat.AppleCoreCompat;
import coolsquid.squidutils.config.AchievementConfigHandler;
import coolsquid.squidutils.config.ArmorMaterialConfigHandler;
import coolsquid.squidutils.config.BiomeConfigHandler;
import coolsquid.squidutils.config.BlockConfigHandler;
import coolsquid.squidutils.config.BlockMaterialConfigHandler;
import coolsquid.squidutils.config.ChestGenConfigHandler;
import coolsquid.squidutils.config.CommandConfigHandler;
import coolsquid.squidutils.config.CrashCallableConfigHandler;
import coolsquid.squidutils.config.CreativeTabConfigHandler;
import coolsquid.squidutils.config.DamageSourceConfigHandler;
import coolsquid.squidutils.config.DimensionConfigHandler;
import coolsquid.squidutils.config.EnchantmentConfigHandler;
import coolsquid.squidutils.config.FishingConfigHandler;
import coolsquid.squidutils.config.FluidConfigHandler;
import coolsquid.squidutils.config.GeneralConfigHandler;
import coolsquid.squidutils.config.ItemConfigHandler;
import coolsquid.squidutils.config.MobConfigHandler;
import coolsquid.squidutils.config.ToolMaterialConfigHandler;
import coolsquid.squidutils.config.compat.botania.BrewConfigHandler;
import coolsquid.squidutils.config.compat.botania.ElvenTradeConfigHandler;
import coolsquid.squidutils.creativetab.ModCreativeTabs;
import coolsquid.squidutils.handlers.AchievementHandler;
import coolsquid.squidutils.handlers.AnvilHandler;
import coolsquid.squidutils.handlers.BlockBoxHandler;
import coolsquid.squidutils.handlers.BonemealHandler;
import coolsquid.squidutils.handlers.BottleHandler;
import coolsquid.squidutils.handlers.CommandHandler;
import coolsquid.squidutils.handlers.CraftingHandler;
import coolsquid.squidutils.handlers.DamageHandler;
import coolsquid.squidutils.handlers.DebugHandler;
import coolsquid.squidutils.handlers.DifficultyHandler;
import coolsquid.squidutils.handlers.DropHandler;
import coolsquid.squidutils.handlers.EntityHandler;
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
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod implements Disableable {

	@Instance
	private static SquidUtils instance;

	public static final ModContainer API = APIHelper.INSTANCE.getAPI("SquidUtils|ScriptingAPI");

	public SquidUtils() {
		super("It's your world. Tweak it in your way.", "226025");
	}

	public static SquidUtils instance() {
		return instance;
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
		GeneralConfigHandler.INSTANCE.init();

		if (GeneralConfigHandler.INSTANCE.modList.length > 0) {
			PackIntegrityChecker.INSTANCE.check();
			FMLCommonHandler.instance().registerCrashCallable(new Modified());
		}
		if (GeneralConfigHandler.INSTANCE.clearRecipes == 1) {
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
		ScriptingAPI.addTrigger("entityjoin", new EntityHandler());
		ScriptingAPI.addTrigger("explosion", new ExplosionHandler());
		ScriptingAPI.addTrigger("interact", new InteractionHandler());
		ScriptingAPI.addTrigger("chat", new ServerChatHandler());

		ScriptHandler.INSTANCE.init();

		if (MiscLib.CLIENT) {
			this.registerHandler(new DifficultyHandler());
			if (!GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("FALSE")) {
				DifficultyHandler.difficulty = GeneralConfigHandler.INSTANCE.forceDifficulty;
			}
		}
		if (!GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("FALSE") && !GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("PEACEFUL") && !GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("EASY") && !GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("NORMAL") && !GeneralConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("HARD")) {
			throw new InvalidConfigValueException("forceDifficulty");
		}
		if (GeneralConfigHandler.INSTANCE.noTNT) {
			this.registerHandler(new TNTHandler());
		}
		if (GeneralConfigHandler.INSTANCE.noAchievements || ScriptHandler.INSTANCE.onAchievement) {
			if (GeneralConfigHandler.INSTANCE.keepTTCoreBug) {
				this.registerHandler(new AchievementHandler() {
					@Override
					@SubscribeEvent
					public final void onAchievement(AchievementEvent event) {
						if (GeneralConfigHandler.INSTANCE.noAchievements) event.setCanceled(true);
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
		if (GeneralConfigHandler.INSTANCE.noWitherBoss) {
			this.registerHandler(new WitherHandler());
		}
		if (GeneralConfigHandler.INSTANCE.chainRecipes) {
			ModRecipes.chain();
		}
		if (GeneralConfigHandler.INSTANCE.noDebug && MiscLib.CLIENT) {
			this.registerHandler(new DebugHandler());
		}
		if (GeneralConfigHandler.INSTANCE.maxRenderDistance != 16 && MiscLib.CLIENT) {
			this.registerHandler(new RenderDistanceHandler());
		}
		if (GeneralConfigHandler.INSTANCE.villagerProtection) {
			this.registerHandler(new VillagerHandler());
		}
		if (GeneralConfigHandler.INSTANCE.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (GeneralConfigHandler.INSTANCE.disableAnvil) {
			this.registerHandler(new AnvilHandler());
		}
		if (ScriptHandler.INSTANCE.onCommand) {
			this.registerHandler(new CommandHandler());
		}
		if (GeneralConfigHandler.INSTANCE.disableTeleportation || ScriptHandler.INSTANCE.onTeleport) {
			this.registerHandler(new TeleportationHandler());
		}
		if (GeneralConfigHandler.INSTANCE.disableBonemeal) {
			this.registerHandler(new BonemealHandler());
		}
		if (GeneralConfigHandler.INSTANCE.disableHoes) {
			this.registerHandler(new ToolHandler());
		}
		if (GeneralConfigHandler.INSTANCE.disableBottleFluidInteraction) {
			this.registerHandler(new BottleHandler());
		}
		if (GeneralConfigHandler.INSTANCE.generateModList != 0) {
			ModLister.INSTANCE.init();
		}
		if (GeneralConfigHandler.INSTANCE.walkSpeed != 0.1F || GeneralConfigHandler.INSTANCE.flySpeed != 0.05F) {
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
		if (GeneralConfigHandler.INSTANCE.explosionSizeMultiplier != 1) {
			this.registerHandler(new ExplosionHandler());
		}
		if (ScriptHandler.INSTANCE.onInteraction) {
			this.registerHandler(new InteractionHandler());
		}
		if (ScriptHandler.INSTANCE.onChat || GeneralConfigHandler.INSTANCE.minMessageLength != 1) {
			this.registerHandler(new ServerChatHandler());
		}
		if (ScriptHandler.INSTANCE.permissions) {
			PermissionHelper.INSTANCE.init();
			this.registerHandler(PermissionHelper.INSTANCE);
		}
		if (GeneralConfigHandler.INSTANCE.worldSize > 0) {
			this.registerHandler(new LivingUpdateHandler());
		}
		if (GeneralConfigHandler.INSTANCE.explodeTNTMinecartsOnCollide) {
			this.registerHandler(new MinecartCollisionHandler());
		}
		if (!GeneralConfigHandler.SETTINGS.getProperty("displayTitle").equals("")) {
			Display.setTitle((String) GeneralConfigHandler.SETTINGS.getProperty("displayTitle"));
		}
		if (MiscLib.CLIENT) {
			if (GeneralConfigHandler.INSTANCE.removeBlockHighlight) {
				MinecraftForge.EVENT_BUS.register(BlockBoxHandler.INSTANCE);
			}
			MinecraftForge.EVENT_BUS.register(new GuiHandler());
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

		ConfigurationManager.INSTANCE.registerHandlers(
				BlockConfigHandler.INSTANCE,
				ItemConfigHandler.INSTANCE,
				ToolMaterialConfigHandler.INSTANCE,
				ArmorMaterialConfigHandler.INSTANCE,
				BiomeConfigHandler.INSTANCE,
				BlockMaterialConfigHandler.INSTANCE,
				MobConfigHandler.INSTANCE,
				AchievementConfigHandler.INSTANCE,
				DamageSourceConfigHandler.INSTANCE,
				CrashCallableConfigHandler.INSTANCE,
				ChestGenConfigHandler.INSTANCE,
				FishingConfigHandler.INSTANCE,
				CreativeTabConfigHandler.INSTANCE,
				EnchantmentConfigHandler.INSTANCE,
				FluidConfigHandler.INSTANCE);

		if (Compat.BOTANIA.isEnabled()) {
			ConfigurationManager.INSTANCE.registerHandlers(
					BrewConfigHandler.INSTANCE,
					ElvenTradeConfigHandler.INSTANCE);
		}

		ConfigurationManager.INSTANCE.loadConfigs(this);

		if (!ToolTipHandler.INSTANCE.getTooltips().isEmpty()) {
			MinecraftForge.EVENT_BUS.register(ToolTipHandler.INSTANCE);
		}

		if (GeneralConfigHandler.INSTANCE.flammabilityMultiplier != 1) {
			for (Object a: Block.blockRegistry) {
				Block b = (Block) a;
				Blocks.fire.setFireInfo(b, Blocks.fire.getEncouragement(b), Blocks.fire.getFlammability(b) * GeneralConfigHandler.INSTANCE.flammabilityMultiplier);
			}
		}

		if (!ItemBanHandler.bannedItems.isEmpty()) {
			this.registerHandler(new ItemBanHandler());
		}

		this.registerHandler(new EntityHandler());

		RegistrySearcher.start();

		if (GeneralConfigHandler.INSTANCE.potionStacks > 1 || GeneralConfigHandler.INSTANCE.pearlStack > 1) {
			StackSizeHandler.some(GeneralConfigHandler.INSTANCE.potionStacks, GeneralConfigHandler.INSTANCE.pearlStack);
		}

		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			this.registerHandler(new DropHandler());
		}

		this.info("Postinitialization finished.");
	}
	
	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (GeneralConfigHandler.INSTANCE.clearRecipes == 2) {
			if (!ContentRemover.isBlacklistedModLoaded()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
		}
		Hooks.save();
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

	public final HashSet<String> commandsToDisable = new HashSet<String>();

	@EventHandler
	public void onServerStarted(FMLServerStartedEvent event) {
		if (GeneralConfigHandler.INSTANCE.removeAllCommands) {
			ServerHelper.getCommands().clear();
		}
		else {
			for (String command: this.commandsToDisable) {
				ServerHelper.removeCommand(command);
			}
		}
		CommandConfigHandler.INSTANCE.init();
		DimensionConfigHandler.INSTANCE.init();
	}
}