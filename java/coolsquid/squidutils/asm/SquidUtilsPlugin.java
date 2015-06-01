/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;

import com.google.common.collect.Maps;

import coolsquid.squidapi.compat.EnumBlacklist;
import coolsquid.squidapi.util.io.IOUtils;
import coolsquid.squidutils.asm.transformers.BlockFallingTransformer;
import coolsquid.squidutils.asm.transformers.BlockPortalTransformer;
import coolsquid.squidutils.asm.transformers.BlockTransformer;
import coolsquid.squidutils.asm.transformers.ChatAllowedCharactersTransformer;
import coolsquid.squidutils.asm.transformers.GameRegistryTransformer;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.ReflectionHelper;

@IFMLLoadingPlugin.TransformerExclusions("coolsquid.squidutils.asm")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class SquidUtilsPlugin implements IFMLLoadingPlugin, IClassTransformer, IFMLCallHook {

	public static final Logger LOGGER = LogManager.getLogger("SquidUtils");
	private static final Map<String, Transformer> toTransform = Maps.newConcurrentMap();

	static {
		toTransform.put("net.minecraft.block.Block", new BlockTransformer());
		toTransform.put("net.minecraft.block.BlockPortal", new BlockPortalTransformer());
		toTransform.put("net.minecraft.block.BlockFalling", new BlockFallingTransformer());
		toTransform.put("net.minecraft.util.ChatAllowedCharacters", new ChatAllowedCharactersTransformer());
		toTransform.put("cpw.mods.fml.common.registry.GameRegistry", new GameRegistryTransformer());
		try {
			SimpleConfig config = new SimpleConfig(new File("./config/SquidUtils/ASMHooks.txt"));
			for (String name: toTransform.keySet()) {
				if (!config.getBoolean(name, true)) {
					toTransform.remove(name);
				}
			}
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		return SquidUtilsPlugin.class.getName();
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public byte[] transform(String untransformedName, String name, byte[] basicClass) {
		if (toTransform.containsKey(name)) {
			LOGGER.info("Transforming " + name);
			ClassNode c = ASMHelper.createClassNodeFromBytes(basicClass);
			toTransform.get(name).transform(c);
			return ASMHelper.getBytesFromClassNode(c);
		}
		return basicClass;
	}

	@Override
	public Void call() throws Exception {
		for (String exclusion: IOUtils.readLines(new File("./config/SquidUtils/TransformerExclusions.txt"))) {
			if (EnumBlacklist.getBlacklister(exclusion) == null) {
				Launch.classLoader.addTransformerExclusion(exclusion);
			}
		}
		try {
			SimpleConfig config = new SimpleConfig(new File("./config/SquidUtils/Transformers.txt"));
			List<IClassTransformer> transformers = ReflectionHelper.getPrivateValue(LaunchClassLoader.class, Launch.classLoader, 3);
			for (int a = 0; a < transformers.size(); a++) {
				IClassTransformer transformer = transformers.get(a);
				if (EnumBlacklist.getBlacklister(transformer) == null && !config.getBoolean(transformer.getClass().getName(), true)) {
					transformers.remove(a);
				}
			}
			config.save("Warning: Do NOT use this config unless you know what you're doing. It can cause major issues." + System.lineSeparator() + "#Be extremely careful with disabling FML's transformers.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}