package coolsquid.squidutils.asm.transformers;

import org.objectweb.asm.tree.ClassNode;

public interface Transformer {
	public abstract void accept(ClassNode c);
}