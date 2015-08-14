package coolsquid.squidutils.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import coolsquid.squidutils.asm.ASMHelper;
import coolsquid.squidutils.asm.Hooks;

public class CreativeTabsTransformer implements Transformer {

	@Override
	public void accept(ClassNode t) {
		MethodNode m = ASMHelper.getMethod(t, "hasSearchBar", "()Z");
		InsnList toInject = new InsnList();
		toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
		toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hooks.getInternalName(), "hasSearchBar", "(Lnet/minecraft/creativetab/CreativeTabs;)Z", false));
		toInject.add(new InsnNode(Opcodes.IRETURN));
		m.instructions.insertBefore(m.instructions.getFirst(), toInject);
	}
}