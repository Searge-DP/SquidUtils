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

public class BlockPortalTransformer implements Transformer {

	@Override
	public void transform(ClassNode c) {
		MethodNode m = ASMHelper.getMethod(c, Names.BLOCK_PORTAL_TP, Names.BLOCK_PORTAL_TP_DESC);
		transformBlockPortal(m);
	}

	private static void transformBlockPortal(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 5));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onEntityCollideWithPortal", "(Lnet/minecraft/entity/Entity;)V", false));
		list.add(new InsnNode(Opcodes.RETURN));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}