package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import com.github.coolsquid.SquidUtils.Handlers.Config.ConfigHandler;

import net.minecraft.block.Block;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class BlockSearcher {
	
	public static final void search() {
		int A = 0;
		while (A != 4095) {
			if (Block.blockRegistry.getObjectById(A) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(A);
				if (ConfigHandler.getAllBlocksUnbreakable()) {
					block.setBlockUnbreakable();
				}
			}
			A++;
		}
	}
}