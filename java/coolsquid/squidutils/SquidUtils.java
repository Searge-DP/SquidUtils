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

import com.google.common.collect.Lists;

import coolsquid.squidapi.SquidAPI;
import coolsquid.squidapi.SquidAPIMod;
import coolsquid.squidapi.compat.Compat;
import coolsquid.squidapi.config.ConfigurationManager;
import coolsquid.squidapi.helpers.server.ServerHelper;
import coolsquid.squidapi.util.EventHandlerManager;
import coolsquid.squidapi.util.MiscLib;
import coolsquid.squidutils.api.SquidUtilsAPI;
import coolsquid.squidutils.api.impl.SquidUtilsAPIImpl;
import coolsquid.squidutils.asm.Hooks;
import coolsquid.squidutils.command.CommandSquidUtils;
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
import coolsquid.squidutils.config.ModListConfigHandler;
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
import coolsquid.squidutils.creativetab.ModCreativeTabs;
import coolsquid.squidutils.handlers.AchievementHandler;
import coolsquid.squidutils.handlers.AnvilHandler;
import coolsquid.squidutils.handlers.BlockBoxHandler;
import coolsquid.squidutils.handlers.BonemealHandler;
import coolsquid.squidutils.handlers.BottleHandler;
import coolsquid.squidutils.handlers.CommonHandler;
import coolsquid.squidutils.handlers.DebugHandler;
import coolsquid.squidutils.handlers.DifficultyHandler;
import coolsquid.squidutils.handlers.DropHandler;
import coolsquid.squidutils.handlers.EntityHandler;
import coolsquid.squidutils.handlers.GuiHandler;
import coolsquid.squidutils.handlers.ItemBanHandler;
import coolsquid.squidutils.handlers.LivingUpdateHandler;
import coolsquid.squidutils.handlers.MinecartCollisionHandler;
import coolsquid.squidutils.handlers.RegistrySearcher;
import coolsquid.squidutils.handlers.RenderDistanceHandler;
import coolsquid.squidutils.handlers.SeedForcer;
import coolsquid.squidutils.handlers.ServerChatHandler;
import coolsquid.squidutils.handlers.SpeedHandler;
import coolsquid.squidutils.handlers.StackSizeHandler;
import coolsquid.squidutils.handlers.TNTHandler;
import coolsquid.squidutils.handlers.TeleportationHandler;
import coolsquid.squidutils.handlers.ToolHandler;
import coolsquid.squidutils.handlers.ToolTipHandler;
import coolsquid.squidutils.handlers.WitherHandler;
import coolsquid.squidutils.scripting.SquidUtilsScripting;
import coolsquid.squidutils.util.ModInfo;
import coolsquid.squidutils.util.ModLister;
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

@Mod(modid = ModInfo.modid, name = ModInfo.name, version = ModInfo.version, dependencies = ModInfo.dependencies, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.7.10")
public class SquidUtils extends SquidAPIMod {

	/**
	 * Instructions may be found at <a href=http://coolsquidmc.blogspot.no/2015/05/using-squidutils-api.html>my website</a>.
	 */
	public static final SquidUtilsAPI API = new SquidUtilsAPIImpl();
	public static final CommonHandler COMMON = new CommonHandler();
	private static final EventHandlerManager handlers = COMMON.getEventHandlerManager();

	@Instance
	private static SquidUtils instance;

	public static SquidUtils instance() {
		return instance;
	}

	public SquidUtils() {
		super("It's your world. Shape it in your way.", Lists.newArrayList("CoolSquid"), "MightyPork, for creating the logo.", null, "http://pastebin.com/raw.php?i=gvAzhu92", 226025);
		this.getMetadata().logoFile = "SquidUtils.png";
	}

	@EventHandler
	private void preInit(FMLPreInitializationEvent event) {
		this.info("Preinitializing.");

		new File("./config/SquidUtils").mkdirs();
		ModConfigHandler.INSTANCE.init();

		if (MiscLib.CLIENT) {
			ModListConfigHandler.INSTANCE.init();
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

		this.info("Preinitialization finished.");
	}

	@EventHandler
	private void init(FMLInitializationEvent event) {
		this.info("Initializing.");

		COMMON.registerCreativeTabs();
		CustomContentManager.INSTANCE.loadAll();

		SquidUtilsScripting.init();

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
		if (ModConfigHandler.INSTANCE.noAchievements) {
			if (ModConfigHandler.INSTANCE.keepTTCoreBug) {
				handlers.registerForgeHandler(new AchievementHandler() {
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
				handlers.registerForgeHandler(new AchievementHandler());
			}
		}
		if (ModConfigHandler.INSTANCE.noWitherBoss) {
			handlers.registerForgeHandler(new WitherHandler());
		}
		if (ModConfigHandler.INSTANCE.noDebug && MiscLib.CLIENT) {
			handlers.registerForgeHandler(new DebugHandler());
		}
		if (ModConfigHandler.INSTANCE.maxRenderDistance != 16 && MiscLib.CLIENT) {
			handlers.registerForgeHandler(new RenderDistanceHandler());
		}
		if (ModConfigHandler.INSTANCE.tabVanilla) {
			ModCreativeTabs.preInit();
		}
		if (ModConfigHandler.INSTANCE.disableAnvil) {
			handlers.registerForgeHandler(new AnvilHandler());
		}
		if (ModConfigHandler.INSTANCE.disableTeleportation ) {
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
		if (MiscLib.CLIENT && ModListConfigHandler.INSTANCE.generateModList != 0) {
			ModLister.INSTANCE.init();
		}
		if (ModConfigHandler.INSTANCE.walkSpeed != 0.1F || ModConfigHandler.INSTANCE.flySpeed != 0.05F) {
			handlers.registerForgeHandler(new SpeedHandler());
		}
		if (Loader.isModLoaded("AppleCore")) {
			AppleCoreCompat.init();
		}
		if (ModConfigHandler.INSTANCE.minMessageLength != 1) {
			handlers.registerForgeHandler(new ServerChatHandler());
		}
		if (ModConfigHandler.INSTANCE.worldSize > 0) {
			handlers.registerForgeHandler(new LivingUpdateHandler());
		}
		if (ModConfigHandler.INSTANCE.explodeTNTMinecartsOnCollide) {
			handlers.registerForgeHandler(new MinecartCollisionHandler());
		}
		if (!ModConfigHandler.INSTANCE.displayTitle.isEmpty()) {
			Display.setTitle(ModConfigHandler.INSTANCE.displayTitle);
		}
		if (MiscLib.CLIENT) {
			if (ModConfigHandler.INSTANCE.removeBlockHighlight) {
				MinecraftForge.EVENT_BUS.register(BlockBoxHandler.INSTANCE);
			}
			MinecraftForge.EVENT_BUS.register(new GuiHandler());
			SquidAPI.COMMON.registerCommand(new CommandSquidUtils());
			if (ModConfigHandler.INSTANCE.defaultSeed != 0) {
				handlers.registerForgeHandler(new SeedForcer());
			}
		}

		this.info("Initialization finished.");
	}

	@EventHandler
	private void postInit(FMLPostInitializationEvent event) {
		this.info("Postinitializing.");

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

		this.info("Postinitialization finished.");
	}

	@EventHandler
	private void finishedLoading(FMLLoadCompleteEvent event) {
		if (MiscLib.CLIENT) {
			COMMON.registerCreativeTabs();
		}
		if (ModConfigHandler.INSTANCE.clearRecipes == 2) {
			for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
				if (MiscLib.getBlacklister(CraftingManager.getInstance().getRecipeList().get(i)) == null) {
					CraftingManager.getInstance().getRecipeList().remove(i);
				}
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
	}

	@EventHandler
	public void onIMC(IMCEvent event) {
		COMMON.getIMCHandler().handleIMCEvent(event);
	}
}