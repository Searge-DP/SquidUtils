/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import coolsquid.squidutils.asm.ASMHelper;
import coolsquid.squidutils.asm.Hooks;
import coolsquid.squidutils.asm.Names;
import coolsquid.squidutils.asm.Transformer;

public class BlockFallingTransformer implements Transformer {

	@Override
	public void transform(ClassNode c) {
		MethodNode m = ASMHelper.getMethod(c, Names.BLOCK_FALLING_UPDATE, Names.BLOCK_FALLING_UPDATE_DESC);
		transformBlockFalling(m);
	}

	private static void transformBlockFalling(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new VarInsnNode(Opcodes.ALOAD, 1));
		list.add(new VarInsnNode(Opcodes.ILOAD, 2));
		list.add(new VarInsnNode(Opcodes.ILOAD, 3));
		list.add(new VarInsnNode(Opcodes.ILOAD, 4));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onBlockFallingUpdate", "(Lnet/minecraft/block/BlockFalling;Lnet/minecraft/world/World;III)V", false));
		list.add(new InsnNode(Opcodes.RETURN));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}