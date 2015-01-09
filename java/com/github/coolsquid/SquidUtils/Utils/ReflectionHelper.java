package com.github.coolsquid.SquidUtils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
	
	public static final String getString(Class<?> c, String fieldName) {
		try {
			Field f = c.getDeclaredField(fieldName);
			String f2 = f.get(f).toString();
			return f2;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static final Object getObject(Class<?> c, String fieldName) {
		try {
			Field f = c.getDeclaredField(fieldName);
			return f;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final Method getMethod(Class<?> c, String methodName) {
		try {
			Method m = c.getDeclaredMethod(methodName);
			return m;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final void invoke(Class<?> c, String methodName) {
		try {
			Method m = c.getDeclaredMethod(methodName);
			m.setAccessible(true);
			m.invoke(true);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
	
	public static final void replaceField(Class<?> c, String fieldName, Object replacement) {
		try {
			Field f = c.getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(f, replacement);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}