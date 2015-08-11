/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils;

import java.io.File;

import minetweaker.MineTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AchievementEvent;

import org.lwjgl.opengl.Display;

import coolsquid.logging.Logger;
import coolsquid.squidapi.compat.Compat;
import coolsquid.squidapi.config.ConfigurationManager;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.logging.MultiLogger;
import coolsquid.squidapi.mod.BaseMod;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.api.SquidUtilsAPI;
import coolsquid.squidutils.api.impl.SquidUtilsAPIImpl;
import coolsquid.squidutils.compat.AppleCoreCompat;
import coolsquid.squidutils.config.AchievementConfigHandler;
import coolsquid.squidutils.config.ArmorMaterialConfigHandler;
import coolsquid.squidutils.config.BiomeConfigHandler;
import coolsquid.squidutils.config.BlockConfigHandler;
import coolsquid.squidutils.config.BlockMaterialConfigHandler;
import coolsquid.squidutils.config.ChestGenConfigHandler;
import coolsquid.squidutils.config.CrashCallableConfigHandler;
import coolsquid.squidutils.config.CreativeTabConfigHandler;
import coolsquid.squidutils.config.DamageSourceConfigHandler;
import coolsquid.squidutils.config.EnchantmentConfigHandler;
import coolsquid.squidutils.config.FishingConfigHandler;
import coolsquid.squidutils.config.FluidConfigHandler;
import coolsquid.squidutils.config.GameOverlayConfigHandler;
import coolsquid.squidutils.config.ItemConfigHandler;
import coolsquid.squidutils.config.MobConfigHandler;
import coolsquid.squidutils.config.ModConfigHandler;
import coolsquid.squidutils.config.ToolMaterialConfigHandler;
import coolsquid.squidutils.config.WorldGenConfigHandler;
import coolsquid.squidutils.config.WorldTypeConfigHandler;
import coolsquid.squidutils.config.compat.botania.BrewConfigHandler;
import coolsquid.squidutils.config.compat.botania.ElvenTradeConfigHandler;
import coolsquid.squidutils.config.compat.minetweaker.BlockUtils;
import coolsquid.squidutils.config.compat.minetweaker.CommandUtils;
import coolsquid.squidutils.config.compat.minetweaker.LogUtils;
import coolsquid.squidutils.config.compat.minetweaker.MiscUtils;
import coolsquid.squidutils.config.compat.minetweaker.RuntimeUtils;
import coolsquid.squidutils.config.compat.minetweaker.StringUtils;
import coolsquid.squidutils.config.compat.minetweaker.SystemUtils;
import coolsquid.squidutils.config.compat.ticon.TiConArrowMaterialConfigHandler;
import coolsquid.squidutils.config.compat.ticon.TiConBowMaterialConfigHandler;
import coolsquid.squidutils.config.compat.ticon.TiConToolMaterialConfigHandler;
import coolsquid.squidutils.config.compat.uptodate.UpToDateConfigHandler;
import coolsquid.squidutils.config.custom.AchievementCreationHandler;
import coolsquid.squidutils.config.custom.ArmorMaterialCreationHandler;
import coolsquid.squidutils.config.custom.BiomeCreationHandler;
import coolsquid.squidutils.config.custom.BlockCreationHandler;
import coolsquid.squidutils.config.custom.BlockMaterialCreationHandler;
import coolsquid.squidutils.config.custom.ChestGenCreationHandler;
import coolsquid.squidutils.config.custom.CrashCallableCreationHandler;
import coolsquid.squidutils.config.custom.CustomContentManager;
import coolsquid.squidutils.config.custom.ItemCreationHandler;
import coolsquid.squidutils.config.custom.RecipeCreationHandler;
import coolsquid.squidutils.config.custom.ShutdownHookCreationHandler;
import coolsquid.squidutils.config.custom.ToolMaterialCreationHandler;
import coolsquid.squidutils.config.custom.UpdateCheckerCreationHandler;
import coolsquid.squidutils.handlers.AchievementHandler;
import coolsquid.squidutils.handlers.AnvilHandler;
import coolsquid.squidutils.handlers.BlockBoxHandler;
import coolsquid.squidutils.handlers.CommonHandler;
import coolsquid.squidutils.handlers.DebugHandler;
import coolsquid.squidutils.handlers.DifficultyHandler;
import coolsquid.squidutils.handlers.DropHandler;
import coolsquid.squidutils.handlers.EntityHandler;
import coolsquid.squidutils.handlers.GuiHandler;
import coolsquid.squidutils.handlers.ItemBanHandler;
import coolsquid.squidutils.handlers.ModEventHandler;
import coolsquid.squidutils.handlers.RegistrySearcher;
import coolsquid.squidutils.handlers.RenderDistanceHandler;
import coolsquid.squidutils.handlers.ServerChatHandler;
import coolsquid.squidutils.handlers.ToolTipHandler;
import coolsquid.squidutils.scripting.SquidUtilsScripting;
import coolsquid.squidutils.util.ModInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.InstanceFactory;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.7.10")
public class SquidUtils extends BaseMod {

	public static final SquidUtilsAPI API = new SquidUtilsAPIImpl();
	public static final SquidUtils INSTANCE = new SquidUtils();
	public static final CommonHandler COMMON = new CommonHandler();

	public static final Logger log = MultiLogger.of("SquidUtils");

	public SquidUtils() {
		this.getMetadata().logoFile = "SquidUtils.png";
		this.getMetadata().description = "It's your world. Shape it in your way.";
	}

	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		log.info("Preinitializing.");

		new File("./config/SquidUtils").mkdirs();
		ModConfigHandler.INSTANCE.init();

		if (MiscLib.CLIENT) {
			if (ModConfigHandler.INSTANCE.clearRecipes == 1) {
				for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
					if (CraftingManager.getInstance().getRecipeList().get(i).getClass().getName().startsWith("net.minecraft.")) {
						CraftingManager.getInstance().getRecipeList().remove(i);
					}
				}
			}
		}

		CustomContentManager.INSTANCE.registerHandlers(
				BlockMaterialCreationHandler.INSTANCE,
				CrashCallableCreationHandler.INSTANCE,
				RecipeCreationHandler.INSTANCE,
				ShutdownHookCreationHandler.INSTANCE,
				UpdateCheckerCreationHandler.INSTANCE,
				AchievementCreationHandler.INSTANCE,
				BiomeCreationHandler.INSTANCE,
				ChestGenCreationHandler.INSTANCE,
				ToolMaterialCreationHandler.INSTANCE,
				ArmorMaterialCreationHandler.INSTANCE,
				ItemCreationHandler.INSTANCE,
				BlockCreationHandler.INSTANCE);

		ConfigurationManager.INSTANCE.registerHandlers(
				CrashCallableConfigHandler.INSTANCE,
				BlockConfigHandler.INSTANCE,
				ItemConfigHandler.INSTANCE,
				ToolMaterialConfigHandler.INSTANCE,
				ArmorMaterialConfigHandler.INSTANCE,
				BlockMaterialConfigHandler.INSTANCE,
				AchievementConfigHandler.INSTANCE,
				DamageSourceConfigHandler.INSTANCE,
				EnchantmentConfigHandler.INSTANCE,
				FluidConfigHandler.INSTANCE,
				BiomeConfigHandler.INSTANCE,
				ChestGenConfigHandler.INSTANCE,
				WorldGenConfigHandler.INSTANCE,
				WorldTypeConfigHandler.INSTANCE,
				FishingConfigHandler.INSTANCE,
				MobConfigHandler.INSTANCE);

		if (Compat.BOTANIA.isEnabled()) {
			ConfigurationManager.INSTANCE.registerHandlers(
					BrewConfigHandler.INSTANCE,
					ElvenTradeConfigHandler.INSTANCE);
		}

		if (Compat.TICON.isEnabled()) {
			ConfigurationManager.INSTANCE.registerHandlers(
					TiConToolMaterialConfigHandler.INSTANCE,
					TiConBowMaterialConfigHandler.INSTANCE,
					TiConArrowMaterialConfigHandler.INSTANCE);
		}

		if (Compat.UPTODATE.isEnabled()) {
			UpToDateConfigHandler.INSTANCE.init();
		}

		if (MiscLib.CLIENT) {
			ConfigurationManager.INSTANCE.registerHandlers(
					GameOverlayConfigHandler.INSTANCE,
					CreativeTabConfigHandler.INSTANCE);
		}

		if (Compat.MINETWEAKER.isEnabled()) {
			MineTweakerAPI.registerClass(BlockUtils.class);
			MineTweakerAPI.registerClass(MiscUtils.class);
			MineTweakerAPI.registerClass(CommandUtils.class);
			MineTweakerAPI.registerClass(LogUtils.class);
			MineTweakerAPI.registerClass(StringUtils.class);
			MineTweakerAPI.registerClass(RuntimeUtils.class);
			MineTweakerAPI.registerClass(SystemUtils.class);
		}

		log.info("Preinitialization finished.");
	}

	@EventHandler
	private void init(FMLInitializationEvent event) {
		log.info("Initializing.");

		if (MiscLib.CLIENT) {
			COMMON.registerCreativeTabs();
		}
		CustomContentManager.INSTANCE.loadAll();

		SquidUtilsScripting.init();

		String d = ModConfigHandler.INSTANCE.forceDifficulty;
		if (MiscLib.CLIENT && !d.equalsIgnoreCase("FALSE") && !d.equalsIgnoreCase("HARDCORE")) {
			DifficultyHandler.DifficultyForcer.difficulty = EnumDifficulty.valueOf(ModConfigHandler.INSTANCE.forceDifficulty.toUpperCase());
			MinecraftForge.EVENT_BUS.register(new DifficultyHandler.DifficultyForcer());
		}
		else if (ModConfigHandler.INSTANCE.forceDifficulty.equalsIgnoreCase("HARDCORE")) {
			MinecraftForge.EVENT_BUS.register(new DifficultyHandler.HardcoreForcer());
		}
		if (!ModConfigHandler.INSTANCE.allowCheats) {
			MinecraftForge.EVENT_BUS.register(new DifficultyHandler.CheatForcer());
		}
		if (ModConfigHandler.INSTANCE.noAchievements) {
			if (ModConfigHandler.INSTANCE.keepTTCoreBug) {
				MinecraftForge.EVENT_BUS.register(new AchievementHandler() {
					@Override
					@SubscribeEvent
					public final void onAchievement(AchievementEvent event) {
						if (ModConfigHandler.INSTANCE.noAchievements) {
							event.setCanceled(true);
						}
					}
				});
			}
			else {
				MinecraftForge.EVENT_BUS.register(new AchievementHandler());
			}
		}
		if (ModConfigHandler.INSTANCE.noDebug && MiscLib.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new DebugHandler());
		}
		if (ModConfigHandler.INSTANCE.maxRenderDistance != 16 && MiscLib.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new RenderDistanceHandler());
		}
		if (ModConfigHandler.INSTANCE.disableAnvil) {
			MinecraftForge.EVENT_BUS.register(new AnvilHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ModConfigHandler.INSTANCE.minMessageLength != 1) {
			MinecraftForge.EVENT_BUS.register(new ServerChatHandler());
		}
		if (!ModConfigHandler.INSTANCE.displayTitle.isEmpty()) {
			Display.setTitle(ModConfigHandler.INSTANCE.displayTitle);
		}
		if (MiscLib.CLIENT) {
			if (ModConfigHandler.INSTANCE.removeBlockHighlight) {
				MinecraftForge.EVENT_BUS.register(BlockBoxHandler.INSTANCE);
			}
			MinecraftForge.EVENT_BUS.register(new GuiHandler());
		}
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());

		log.info("Initialization finished.");
	}

	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		log.info("Postinitializing.");

		ConfigurationManager.INSTANCE.loadConfigs(this.getContainer());

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
			MinecraftForge.EVENT_BUS.register(new ItemBanHandler());
		}

		MinecraftForge.EVENT_BUS.register(new EntityHandler());

		RegistrySearcher.start();

		if (!DropHandler.shouldclear.isEmpty() || !DropHandler.dropstoremove.isEmpty() || !DropHandler.drops.isEmpty()) {
			MinecraftForge.EVENT_BUS.register(new DropHandler());
		}

		log.info("Postinitialization finished.");
	}

	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		COMMON.finishedLoading();
		if (ModConfigHandler.INSTANCE.clearRecipes == 2) {
			for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
				if (MiscLib.getBlacklister(CraftingManager.getInstance().getRecipeList().get(i)) == null) {
					CraftingManager.getInstance().getRecipeList().remove(i);
				}
			}
		}
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
	}

	@EventHandler
	public void onIMC(IMCEvent event) {
		COMMON.getIMCHandler().handleIMCEvent(event);
	}

	@InstanceFactory
	private static SquidUtils instance() {
		return INSTANCE;
	}

	@Override
	public String getUpdateUrl() {
		return "http://pastebin.com/raw.php?i=gvAzhu92";
	}
}