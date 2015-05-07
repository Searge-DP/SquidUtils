package coolsquid.squidutils.compat.minetweaker;

import java.io.File;
import java.util.Collection;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.google.gson.Gson;

import coolsquid.squidapi.reflection.FieldHelper;
import coolsquid.squidapi.reflection.ReflectionHelper;
import coolsquid.squidapi.util.Utils;
import coolsquid.squidapi.util.io.IOUtils;

@ZenClass("mods.squidutils.ReflectionHelper")
public class MinetweakerAddon {

	private static Gson gson = new Gson();

	@ZenMethod
	public static void setField(String clazz, String field, String type, String json) {
		FieldHelper f = getField(clazz, field);
		f.set(create(json, type));
	}

	@ZenMethod
	public static void setFinalField(String clazz, String field, String type, String json) {
		FieldHelper f = getFinalField(clazz, field);
		f.set(create(json, type));
	}

	@ZenMethod
	public static void setField(String clazz, String field, String type) {
		setField(clazz, field, type, "");
	}

	@ZenMethod
	public static void setFinalField(String clazz, String field, String type) {
		setFinalField(clazz, field, type, "");
	}

	@ZenMethod
	public static void addTo(String clazz, String field, String type, String json) {
		Collection<Object> c = getField(clazz, field).get();
		c.add(create(json, type));
	}

	@ZenMethod
	public static void addTo(String clazz, String field, String type) {
		addTo(clazz, field, type, "");
	}

	private static FieldHelper getField(String clazz, String field) {
		return ReflectionHelper.in(Utils.getClass(clazz)).field(field);
	}

	private static FieldHelper getFinalField(String clazz, String field) {
		return ReflectionHelper.in(Utils.getClass(clazz)).finalField(field);
	}

	private static Object create(String json, String type) {
		return json.equals("null") ? null : gson.fromJson(json.isEmpty() ? "{}" : IOUtils.readAll(new File("./" + json + ".json")), Utils.getClass(type));
	}
}