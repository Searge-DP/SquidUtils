package com.github.coolsquid.SquidUtils.Item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 
 * @author CoolSquid
 *
 */

@Deprecated
public class IGlassBottle extends ItemGlassBottle {
	
	public void blockClicked(ItemStack itemStack, World world, EntityPlayer player) {
		MovingObjectPosition position = getMovingObjectPositionFromPlayer(world, player, true);
		if (position == null || !(position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
			return;
		}
		int x = position.blockX;
		int y = position.blockY;
		int z = position.blockZ;
		if (world.getBlock(x, y, z) == Blocks.water) {
			itemStack.stackSize--;
			if (player.inventory.addItemStackToInventory(new ItemStack(Items.potionitem, 1)));
			else
				player.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
		}
	}
	
	public MovingObjectPosition blockCheck(World world, EntityPlayer player) {
		MovingObjectPosition position = getMovingObjectPositionFromPlayer(world, player, true);
		return position;
	}
}