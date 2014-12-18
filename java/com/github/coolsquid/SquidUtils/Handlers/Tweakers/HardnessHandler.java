package com.github.coolsquid.SquidUtils.Handlers.Tweakers;

import net.minecraft.block.Block;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class HardnessHandler {
	
	public static final void blockSearch() {
		int A = 0;
		while (A != 4095) {
			if (Block.blockRegistry.getObjectById(A) != null) {
				Block block = (Block) Block.blockRegistry.getObjectById(A);
				block.setBlockUnbreakable();
			}
			A++;
		}
	}
}