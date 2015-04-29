/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;

import org.lwjgl.opengl.Display;

import coolsquid.squidapi.Disableable;
import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.SquidAPIMod;
import coolsquid.squidapi.compat.Compat;
import coolsquid.squidapi.config.ConfigurationManager;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.util.ContentRemover;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidutils.api.SquidUtilsAPI;
import coolsquid.squidutils.api.eventhandler.EventHandlerManager;
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
import coolsquid.squidutils.config.ItemConfigHandler;
import coolsquid.squidutils.config.MobConfigHandler;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.config.ModListConfigHandler;
import coolsquid.squidutils.config.ToolMaterialConfigHandler;
import coolsquid.squidutils.config.WorldGenConfigHandler;
import coolsquid.squidutils.config.compat.botania.BrewConfigHandler;
import coolsquid.squidutils.config.compat.botania.ElvenTradeConfigHandler;
import coolsquid.squidutils.creativetab.ModCreativeTabs;
import coolsquid.squidutils.handlers.AchievementHandler;
import coolsquid.squidutils.handlers.AnvilHandler;
import coolsquid.squidutils.handlers.BlockBoxHandler;
import coolsquid.squidutils.handlers.BonemealHandler;
import coolsquid.squidutils.handlers.BottleHandler;
import coolsquid.squidutils.handlers.CommandHandler;
import coolsquid.squidutils.handlers.CommonHandler;
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
import coolsquid.squidutils.handlers.SeedForcer;
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
import coolsquid.squidutils.util.ModInfo;
import coolsquid.squidutils.util.ModLister;
import coolsquid.squidutils.util.script.EventEffectHelper;
import coolsquid.squidutils.util.script.EventInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*")
public class SquidUtils extends SquidAPIMod implements Disableable {

	public static final SquidUtilsAPI API = new SquidUtilsAPI();
	public static final CommonHandler COMMON = new CommonHandler();
	private static final EventHandlerManager handlers = COMMON.getEventHandlerManager();

	@Instance
	private static SquidUtils instance;

	public static SquidUtils instance() {
		return instance;
	}

	public SquidUtils() {
		super("It's your world. Shape it in your way.");
	}

	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		this.info("Preinitializing.");
		this.info("Version id: ", this.hash());

		new File("./config/SquidUtils").mkdirs();
		ModConfigHandler.INSTANCE.init();

		ModListConfigHandler.INSTANCE.init();

		if (ModConfigHandler.INSTANCE.clearRecipes == 1) {
			CraftingManager.getInstance().getRecipeList().clear();
		}

		this.info("Preinitialization finished.");
	}

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

		API.getScripting().addTrigger("achievement", new AchievementHandler());
		API.getScripting().addTrigger("command", new CommandHandler());
		API.getScripting().addTrigger("teleport", new TeleportationHandler());
		API.getScripting().addTrigger("craft", new CraftingHandler());
		API.getScripting().addTrigger("smelt", new SmeltingHandler());
		API.getScripting().addTrigger("hurt", new DamageHandler());
		API.getScripting().addTrigger("heal", new HealingHandler());
		API.getScripting().addTrigger("toss", new TossHandler());
		API.getScripting().addTrigger("entityjoin", new EntityHandler());
		API.getScripting().addTrigger("explosion", new ExplosionHandler());
		API.getScripting().addTrigger("interact", new InteractionHandler());
		API.getScripting().addTrigger("chat", new ServerChatHandler());

		ScriptHandler.INSTANCE.init();

		String d = ModConfigHandler.INSTANCE.forceDifficulty;
		if (MiscLib.CLIENT && !d.equalsIgnoreCase("FALSE") && !d.equalsIgnoreCase("HARDCORE")) {
			DifficultyHandler.DifficultyForcer.difficulty = EnumDifficulty.valueOf(ModConfigHandler.INSTANCE.forceDifficulty.toUpperCase());
			handlers.registerForgeHandler(new DifficultyHandler.DifficultyForcer());
		}
		else if (ModConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("HARDCORE")) {
			handlers.registerForgeHandler(new DifficultyHandler.HardcoreForcer());
		}
		if (!ModConfigHandler.INSTANCE.allowCheats) {
			handlers.registerForgeHandler(new DifficultyHandler.CheatForcer());
		}
		if (ModConfigHandler.INSTANCE.noTNT) {
			handlers.registerForgeHandler(new TNTHandler());
		}
		if (ModConfigHandler.INSTANCE.noAchievements || ScriptHandler.INSTANCE.onAchievement) {
			if (ModConfigHandler.INSTANCE.keepTTCoreBug) {
				handlers.registerForgeHandler(new AchievementHandler() {
					@Override
					@SubscribeEvent
					public final void onAchievement(AchievementEvent event) {
						if (ModConfigHandler.INSTANCE.noAchievements) {
							event.setCanceled(true);
						}
						for (EventInfo a: info) {
							EventEffectHelper.performEffects(a, event.entityLiving);
							if (a.values.containsKey("cancel")) {
								event.setCanceled(true);
							}
						}
					}
				});
			}
			else {
				handlers.registerForgeHandler(new AchievementHandler());
			}
		}
		if (ModConfigHandler.INSTANCE.noWitherBoss) {
			handlers.registerForgeHandler(new WitherHandler());
		}
		if (ModConfigHandler.INSTANCE.chainRecipes) {
			ModRecipes.chain();
		}
		if (ModConfigHandler.INSTANCE.noDebug && MiscLib.CLIENT) {
			handlers.registerForgeHandler(new DebugHandler());
		}
		if (ModConfigHandler.INSTANCE.maxRenderDistance != 16 && MiscLib.CLIENT) {
			handlers.registerForgeHandler(new RenderDistanceHandler());
		}
		if (ModConfigHandler.INSTANCE.villagerProtection) {
			handlers.registerForgeHandler(new VillagerHandler());
		}
		if (ModConfigHandler.INSTANCE.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ModConfigHandler.INSTANCE.disableAnvil) {
			handlers.registerForgeHandler(new AnvilHandler());
		}
		if (ScriptHandler.INSTANCE.onCommand) {
			handlers.registerForgeHandler(new CommandHandler());
		}
		if (ModConfigHandler.INSTANCE.disableTeleportation || ScriptHandler.INSTANCE.onTeleport) {
			handlers.registerForgeHandler(new TeleportationHandler());
		}
		if (ModConfigHandler.INSTANCE.disableBonemeal) {
			handlers.registerForgeHandler(new BonemealHandler());
		}
		if (ModConfigHandler.INSTANCE.disableHoes) {
			handlers.registerForgeHandler(new ToolHandler());
		}
		if (ModConfigHandler.INSTANCE.disableBottleFluidInteraction) {
			handlers.registerForgeHandler(new BottleHandler());
		}
		if (ModListConfigHandler.INSTANCE.generateModList != 0) {
			ModLister.INSTANCE.init();
		}
		if (ModConfigHandler.INSTANCE.walkSpeed != 0.1F || ModConfigHandler.INSTANCE.flySpeed != 0.05F) {
			handlers.registerForgeHandler(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ScriptHandler.INSTANCE.onCraft) {
			handlers.registerFMLHandler(new CraftingHandler());
		}
		if (ScriptHandler.INSTANCE.onSmelt) {
			handlers.registerFMLHandler(new SmeltingHandler());
		}
		if (ScriptHandler.INSTANCE.onHurt) {
			handlers.registerForgeHandler(new DamageHandler());
		}
		if (ScriptHandler.INSTANCE.onHeal) {
			handlers.registerForgeHandler(new HealingHandler());
		}
		if (ScriptHandler.INSTANCE.onToss) {
			handlers.registerForgeHandler(new TossHandler());
		}
		if (ModConfigHandler.INSTANCE.explosionSizeMultiplier != 1) {
			handlers.registerForgeHandler(new ExplosionHandler());
		}
		if (ScriptHandler.INSTANCE.onInteraction) {
			handlers.registerForgeHandler(new InteractionHandler());
		}
		if (ScriptHandler.INSTANCE.onChat || ModConfigHandler.INSTANCE.minMessageLength != 1) {
			handlers.registerForgeHandler(new ServerChatHandler());
		}
		if (ScriptHandler.INSTANCE.permissions) {
			PermissionHelper.INSTANCE.init();
			handlers.registerForgeHandler(PermissionHelper.INSTANCE);
		}
		if (ModConfigHandler.INSTANCE.worldSize > 0) {
			handlers.registerForgeHandler(new LivingUpdateHandler());
		}
		if (ModConfigHandler.INSTANCE.explodeTNTMinecartsOnCollide) {
			handlers.registerForgeHandler(new MinecartCollisionHandler());
		}
		if (!ModConfigHandler.SETTINGS.getProperty("displayTitle").equals("")) {
			Display.setTitle((String) ModConfigHandler.SETTINGS.getProperty("displayTitle"));
		}
		if (MiscLib.CLIENT) {
			if (ModConfigHandler.INSTANCE.removeBlockHighlight) {
				MinecraftForge.EVENT_BUS.register(BlockBoxHandler.INSTANCE);
			}
			MinecraftForge.EVENT_BUS.register(new GuiHandler());
			SquidAPI.instance().registerCommands(new CommandSquidUtils());
			if (ModConfigHandler.INSTANCE.defaultSeed != 0) {
				handlers.registerForgeHandler(new SeedForcer());
			}
		}

		Utils.runVersionCheckerCompat("226025");

		this.info("Initialization finished.");
	}

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
				FluidConfigHandler.INSTANCE,
				WorldGenConfigHandler.INSTANCE);

		if (Compat.BOTANIA.isEnabled()) {
			ConfigurationManager.INSTANCE.registerHandlers(
					BrewConfigHandler.INSTANCE,
					ElvenTradeConfigHandler.INSTANCE);
		}

		ConfigurationManager.INSTANCE.loadConfigs(this);

		if (!COMMON.getTooltips().isEmpty()) {
			MinecraftForge.EVENT_BUS.register(ToolTipHandler.INSTANCE);
		}

		if (ModConfigHandler.INSTANCE.flammabilityMultiplier != 1) {
			for (Object a: Block.blockRegistry) {
				Block b = (Block) a;
				Blocks.fire.setFireInfo(b, Blocks.fire.getEncouragement(b), Blocks.fire.getFlammability(b) * ModConfigHandler.INSTANCE.flammabilityMultiplier);
			}
		}

		if (!COMMON.getBannedItems().isEmpty()) {
			handlers.registerForgeHandler(new ItemBanHandler());
		}

		handlers.registerForgeHandler(new EntityHandler());

		RegistrySearcher.start();

		if (ModConfigHandler.INSTANCE.potionStacks > 1 || ModConfigHandler.INSTANCE.pearlStack > 1) {
			StackSizeHandler.some(ModConfigHandler.INSTANCE.potionStacks, ModConfigHandler.INSTANCE.pearlStack);
		}

		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			handlers.registerForgeHandler(new DropHandler());
		}

		this.info("Postinitialization finished.");for (String a: API.getMaterials().names()) {
			this.info(a);
		}
	}

	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (ModConfigHandler.INSTANCE.clearRecipes == 2) {
			if (!ContentRemover.isBlacklistedModLoaded()) {
				CraftingManager.getInstance().getRecipeList().clear();
			}
		}
		Hooks.save();
	}

	@EventHandler
	public void onServerStarted(FMLServerStartedEvent event) {
		if (ModConfigHandler.INSTANCE.removeAllCommands) {
			ServerHelper.getCommands().clear();
		}
		else {
			for (String command: COMMON.getDisabledCommands()) {
				ServerHelper.removeCommand(command);
			}
		}
		CommandConfigHandler.INSTANCE.init();
		DimensionConfigHandler.INSTANCE.init();
	}

	@Override
	public void disable() {
		handlers.unregisterAll();
		this.setEnabledState(false);
		this.info("SquidUtils has been disabled.");
	}

	@Override
	public void enable() {
		handlers.registerAll();
		this.setEnabledState(true);
		this.info("SquidUtils has been enabled.");
	}

	@EventHandler
	public void onIMC(IMCEvent event) {
		API.getIMCHandler().handleIMCEvent(event);
	}
}