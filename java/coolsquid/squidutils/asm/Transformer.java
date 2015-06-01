/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import org.objectweb.asm.tree.ClassNode;

public interface Transformer {
	public abstract void transform(ClassNode c);
}