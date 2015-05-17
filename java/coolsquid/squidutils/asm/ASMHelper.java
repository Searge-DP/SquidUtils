/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import net.minecraft.launchwrapper.Launch;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import coolsquid.squidapi.exception.ASMException;

public class ASMHelper {

	public static final boolean DEV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static MethodNode getMethod(ClassNode c, String s, String s2) {
		for (MethodNode m: c.methods ) {
			if (s.equals(m.name) && s2.equals(m.desc)) {
				return m;
			}
		}
		throw new ASMException("Could not find method: " + s + s2);
	}

	public static FieldNode getField(ClassNode c, String s) {
		for (FieldNode f: c.fields ) {
			if (s.equals(f.name)) {
				return f;
			}
		}
		throw new ASMException("Could not find field: " + s);
	}

	public static byte[] getBytesFromClassNode(ClassNode c) {
		ClassWriter w = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		c.accept(w);
		byte[] b = w.toByteArray();
		SquidUtilsPlugin.LOGGER.info("Successfully transformed " + c.name.replace("/", "."), ".");
		return b;
	}

	public static ClassNode createClassNodeFromBytes(byte[] b) {
		ClassNode c = new ClassNode();
		ClassReader r = new ClassReader(b);
		r.accept(c, ClassReader.EXPAND_FRAMES);
		return c;
	}

	public static byte[] getBytesFromClassNodeNoMsg(ClassNode c) {
		ClassWriter w = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		c.accept(w);
		byte[] b = w.toByteArray();
		return b;
	}
}