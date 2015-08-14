/*******************************************************************************
 * Copyright (c) 2015 CoolSquid.
 * All rights reserved.
 *******************************************************************************/
package coolsquid.squidutils.asm;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;

import com.google.common.collect.Maps;

import coolsquid.squidutils.asm.transformers.BlockFallingTransformer;
import coolsquid.squidutils.asm.transformers.BlockPortalTransformer;
import coolsquid.squidutils.asm.transformers.ChatAllowedCharactersTransformer;
import coolsquid.squidutils.asm.transformers.CreativeTabsTransformer;
import coolsquid.squidutils.asm.transformers.Transformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("SquidUtils")
@IFMLLoadingPlugin.TransformerExclusions("coolsquid.squidutils.asm")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class SquidUtilsPlugin implements IFMLLoadingPlugin, IClassTransformer {

	public static final Logger LOGGER = LogManager.getFormatterLogger("SquidUtils");
	private static final Map<String, Transformer> toTransform = Maps.newConcurrentMap();

	static {
		toTransform.put("net.minecraft.block.BlockPortal", new BlockPortalTransformer());
		toTransform.put("net.minecraft.block.BlockFalling", new BlockFallingTransformer());
		toTransform.put("net.minecraft.util.ChatAllowedCharacters", new ChatAllowedCharactersTransformer());
		toTransform.put("net.minecraft.creativetab.CreativeTabs", new CreativeTabsTransformer());
		try {
			File file = new File("./config/SquidUtils/ASMHooks.txt");
			if (file.exists()) {
				SimpleConfig config = new SimpleConfig(file);
				for (String name: toTransform.keySet()) {
					if (!config.getBoolean(name, true)) {
						toTransform.remove(name);
					}
				}
				config.save();
			}
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
	public byte[] transform(String untransformedName, String name, byte[] basicClass) {
		if (toTransform.containsKey(name)) {
			LOGGER.info("Transforming " + name);
			ClassNode c = ASMHelper.createClassNodeFromBytes(basicClass);
			try {
				toTransform.get(name).accept(c);
				return ASMHelper.getBytesFromClassNode(c);
			} catch (SkipMeException e) {
				LOGGER.warn("Failed to transform %s!", name);
			}
		}
		return basicClass;
	}

	public static void skip() {
		throw new SkipMeException();
	}
}