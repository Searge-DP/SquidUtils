package coolsquid.squidutils.handlers;

import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import coolsquid.squidutils.config.ModConfigHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAchievement(AchievementEvent event) {
		if (ModConfigHandler.INSTANCE.noAchievements) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onWorldLoad(Load event) {
		if (ModConfigHandler.INSTANCE.defaultSeed != 0) {
			event.world.getWorldInfo().randomSeed = ModConfigHandler.INSTANCE.defaultSeed;
		}
	}
}