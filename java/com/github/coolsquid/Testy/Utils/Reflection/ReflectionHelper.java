package com.github.coolsquid.Testy.Utils.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ReflectionHelper {
	
	public static final String getString(Class<?> c, String fieldName) {
		try {
			Field f = c.getDeclaredField(fieldName);
			f.setAccessible(true);
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
			f.setAccessible(true);
			return f;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final Method getMethod(Class<?> c, String methodName) {
		try {
			Method m = c.getDeclaredMethod(methodName);
			m.setAccessible(true);
			return m;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final void invoke(String className, String methodName) {
		try {
			Class<?> c = Class.forName(className);
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
			
			Field m = Field.class.getDeclaredField("modifiers");
			m.setAccessible(true);
			m.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			
			f.setAccessible(true);
			f.set(f, replacement);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
}