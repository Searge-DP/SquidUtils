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
import coolsquid.squidutils.asm.SquidUtilsPlugin;

public class ChatAllowedCharactersTransformer implements Transformer {

	@Override
	public void accept(ClassNode c) {
		if (this.hasIntelliInput()) {
			SquidUtilsPlugin.LOGGER.warn("IntelliInput is loaded. Due to the way IntelliInput inserts its hooks, SquidUtils cannot perform its own hooks.");
			SquidUtilsPlugin.skip();
		}
		MethodNode m = ASMHelper.getMethod(c, Names.IS_ALLOWED_CHAR, "(C)Z");
		if (m != null) {
			transformAllowedChars(m);
		}
		else {
			SquidUtilsPlugin.skip();
		}
	}

	private boolean hasIntelliInput() {
		try {
			Class.forName("com.tsoft_web.IntelliInput.asm.IntelliInputCorePlugin");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static void transformAllowedChars(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ILOAD, 0));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "isAllowedChar", "(C)Z", false));
		list.add(new InsnNode(Opcodes.IRETURN));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}