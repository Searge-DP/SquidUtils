/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.config;

import java.io.File;

import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import coolsquid.squidapi.config.ConfigHandler;
import coolsquid.squidutils.SquidUtils;

public class GameOverlayConfigHandler extends ConfigHandler {

	public static final GameOverlayConfigHandler INSTANCE = new GameOverlayConfigHandler(new File("./config/SquidUtils/GameOverlays.cfg"));

	public GameOverlayConfigHandler(File file) {
		super(file);
	}

	@Override
	public void loadConfig() {
		for (ElementType overlay: ElementType.values()) {
			if (!this.config.get(overlay.toString(), "enable", true).getBoolean()) {
				SquidUtils.COMMON.disableOverlay(overlay);
			}
		}
	}
}