/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import net.minecraft.launchwrapper.Launch;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import coolsquid.squidapi.util.objects.RuntimeInfo;

public class ASMHelper {

	public static final boolean DEV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static MethodNode getMethod(ClassNode c, String s, String s2) {
		for (MethodNode m: c.methods ) {
			if (s.equals(m.name) && s2.equals(m.desc)) {
				return m;
			}
		}
		SquidUtilsPlugin.LOGGER.error(String.format("Could not find method %s%s.", s, s2));
		SquidUtilsPlugin.LOGGER.error("This is a critical error. Certain features may not function. Please report this error to CoolSquid with a copy of minecraft/SquidAPI/RuntimeInfo.txt.");
		try (PrintStream printer = new PrintStream(FileUtils.openOutputStream(new File("./SquidAPI/Runtime.txt")))) {
			new RuntimeInfo().print(printer);
		} catch (IOException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		logClass(c);
		return null;
	}

	private static void logClass(ClassNode c) {
		SquidUtilsPlugin.LOGGER.info("Methods:");
		for (MethodNode m: c.methods) {
			SquidUtilsPlugin.LOGGER.info(m.name + m.desc);
		}
		SquidUtilsPlugin.LOGGER.info("Fields:");
		for (FieldNode m: c.fields) {
			SquidUtilsPlugin.LOGGER.info(m.name);
		}
	}

	public static FieldNode getField(ClassNode c, String s) {
		for (FieldNode f: c.fields ) {
			if (s.equals(f.name)) {
				return f;
			}
		}
		SquidUtilsPlugin.LOGGER.error(String.format("Could not find field %s.", s));
		SquidUtilsPlugin.LOGGER.error("This is a critical error. Certain features may not function. Please report this error to CoolSquid with a copy of minecraft/SquidAPI/RuntimeInfo.txt.");
		try (PrintStream printer = new PrintStream(FileUtils.openOutputStream(new File("./SquidAPI/Runtime.txt")))) {
			new RuntimeInfo().print(printer);
		} catch (IOException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
		logClass(c);
		return null;
	}

	public static byte[] getBytesFromClassNode(ClassNode c) {
		ClassWriter w = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		c.accept(w);
		byte[] b = w.toByteArray();
		SquidUtilsPlugin.LOGGER.info("Successfully transformed %s.", c.name.replace("/", "."));
		return b;
	}

	public static ClassNode createClassNodeFromBytes(byte[] b) {
		ClassNode c = new ClassNode();
		ClassReader r = new ClassReader(b);
		r.accept(c, ClassReader.EXPAND_FRAMES);
		return c;
	}

	public static AnnotationNode getAnnotation(ClassNode c, String desc) {
		for (AnnotationNode a: c.visibleAnnotations) {
			if (desc.equals(a.desc)) {
				return a;
			}
		}
		return null;
	}
}