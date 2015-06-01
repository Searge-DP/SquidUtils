/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm.transformers;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import coolsquid.squidutils.asm.ASMHelper;
import coolsquid.squidutils.asm.Transformer;

public class GameRegistryTransformer implements Transformer {

	@Override
	public void transform(ClassNode c) {
		MethodNode m = ASMHelper.getMethod(c, "registerBlock", "(Lnet/minecraft/block/Block;Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/block/Block;");
		transformRegisterBlock(m);
		MethodNode m2 = ASMHelper.getMethod(c, "registerItem", "(Lnet/minecraft/item/Item;Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/item/Item;");
		transformRegisterItem(m2);
	}

	private static void transformRegisterBlock(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new VarInsnNode(Opcodes.ALOAD, 2));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "coolsquid/squidutils/asm/Hooks", "registerBlock", "(Lnet/minecraft/block/Block;Ljava/lang/String;)Z", false));
		Label l1 = new Label();
		list.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(l1)));
		Label l2 = new Label();
		list.add(new LabelNode(l2));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new InsnNode(Opcodes.ARETURN));
		list.add(new LabelNode(l1));
		list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}

	private static void transformRegisterItem(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new VarInsnNode(Opcodes.ALOAD, 1));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "coolsquid/squidutils/asm/Hooks", "registerItem", "(Lnet/minecraft/item/Item;Ljava/lang/String;)Z", false));
		Label l1 = new Label();
		list.add(new JumpInsnNode(Opcodes.IFNE, new LabelNode(l1)));
		Label l2 = new Label();
		list.add(new LabelNode(l2));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new InsnNode(Opcodes.ARETURN));
		list.add(new LabelNode(l1));
		list.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}