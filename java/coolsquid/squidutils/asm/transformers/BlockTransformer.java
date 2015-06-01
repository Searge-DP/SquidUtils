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

public class BlockTransformer implements Transformer {

	@Override
	public void transform(ClassNode c) {
		MethodNode m = ASMHelper.getMethod(c, Names.SET_HARDNESS, Names.DESC);
		transformSetter(m, "onSetHardness");
		MethodNode m2 = ASMHelper.getMethod(c, Names.SET_RESISTANCE, Names.DESC);
		transformSetter(m2, "onSetResistance");
		MethodNode m3 = ASMHelper.getMethod(c, Names.SET_LIGHTLEVEL, Names.DESC);
		transformSetter(m3, "onSetLightLevel");
	}

	private static void transformSetter(MethodNode m, String hook) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new VarInsnNode(Opcodes.FLOAD, 1));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), hook, "(Lnet/minecraft/block/Block;F)V", false));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new InsnNode(Opcodes.ARETURN));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}