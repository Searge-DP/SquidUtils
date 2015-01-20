package com.github.coolsquid.Testy.Registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Registry extends SimpleRegistry {
	
	protected static Map<Object, Integer> m = new HashMap<Object, Integer>();
	
	private static int a = 0;
	
	public Registry(int i) {
		super(i);
	}
	
	public Registry() {}
	
	/**
	 * Registers an object to the registry, as long as the registry is smaller than the max size.
	 * @param object
	 */
	
	@Override
	public void register(Object object) {
		if (a < maxSize) {
			if (m.get(object) == null) {
				l.add(object);
				m.put(object, a);
			}
			a++;
		}
		else {
			throw new RegistryException("The maximum size was reached!");
		}
	}
	
	public int get(Object o) {
		return m.get(o);
	}
}