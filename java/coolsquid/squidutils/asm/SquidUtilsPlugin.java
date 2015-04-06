/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import coolsquid.squidutils.util.Hooks;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class SquidUtilsPlugin implements IFMLLoadingPlugin, IClassTransformer {

	public static final Logger LOGGER = LogManager.getLogger("SquidUtils");

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {SquidUtilsPlugin.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.block.Block")) {
			LOGGER.info("Transforming " + transformedName);
			ClassNode c = ASMHelper.createClassNode(basicClass);
			MethodNode m = ASMHelper.getMethod(c, Methods.SET_HARDNESS, Methods.DESC);
			transformSetter(m, "onSetHardness");
			MethodNode m2 = ASMHelper.getMethod(c, Methods.SET_RESISTANCE, Methods.DESC);
			transformSetter(m2, "onSetResistance");
			MethodNode m3 = ASMHelper.getMethod(c, Methods.SET_LIGHTLEVEL, Methods.DESC);
			transformSetter(m3, "onSetLightLevel");
			basicClass = ASMHelper.getBytesFromClassNode(c);
		}
		else if (transformedName.equals("net.minecraft.block.BlockPortal")) {
			LOGGER.info("Transforming " + transformedName);
			ClassNode c = ASMHelper.createClassNode(basicClass);
			MethodNode m = ASMHelper.getMethod(c, "onEntityCollidedWithBlock", "(Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)V");
			transformBlockPortal(m);
			basicClass = ASMHelper.getBytesFromClassNode(c);
		}
		return basicClass;
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

	private static void transformBlockPortal(MethodNode m) {
		InsnList list = new InsnList();

		list.add(new VarInsnNode(Opcodes.ALOAD, 5));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onEntityCollideWithPortal", "(Lnet/minecraft/entity/Entity;)V", false));
		list.add(new InsnNode(Opcodes.RETURN));

		m.instructions.insertBefore(m.instructions.getFirst(), list);
	}
}